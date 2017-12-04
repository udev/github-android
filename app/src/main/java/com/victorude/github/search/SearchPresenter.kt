package com.victorude.github.search

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.SearchView
import com.jakewharton.rxbinding2.widget.RxSearchView
import com.jakewharton.rxbinding2.widget.SearchViewQueryTextEvent
import com.victorude.github.BasePresenterImpl
import com.victorude.github.R
import com.victorude.github.common.ARG_REPO
import com.victorude.github.common.ARG_USER
import com.victorude.github.model.Repo
import com.victorude.github.model.Result
import com.victorude.github.repo.RepoDetailFragment
import com.victorude.github.service.GitHubService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

open class SearchPresenter @Inject constructor() : BasePresenterImpl<String>() {

    @Inject
    lateinit var github: GitHubService
    lateinit var search: SearchView
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun setView(view: View) {
        super.setView(view)

        this.search = mvpView.findViewById(R.id.search)

        val searchIntent: Disposable = getIntent().startWith("")
                .skipWhile { queryString -> queryString.isNotBlank() }
                .doOnNext({ queryString -> searchGithub(queryString) })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

        compositeDisposable.add(searchIntent)
    }

    private fun searchGithub(queryString: String) {
        github.search(mapOf("q" to queryString))
                .enqueue(object : Callback<Result<List<Repo>>> {
                    override fun onFailure(call: Call<Result<List<Repo>>>?, t: Throwable?) {
                        //TODO: update error handling to notify the user
                        Timber.tag("TAG").d(t?.message, t)
                    }

                    override fun onResponse(call: Call<Result<List<Repo>>>?, response: Response<Result<List<Repo>>>?) {
                        val results: Result<List<Repo>> = response!!.body() ?: return
                        val resultsView: RecyclerView = mvpView.findViewById(R.id.results)
                        resultsView.layoutManager = LinearLayoutManager(mvpView.context)
                        resultsView.adapter = SearchResultAdapter(results, object : SearchItemClickListener() {
                                    override fun onItemClick(user: String, repo: String) {
                                        showDetails(user, repo)
                                    }

                                })
                    }
                })
    }

    private fun showDetails(user: String, repo: String) {
        val fragment = RepoDetailFragment()
        val bundle = Bundle()
        bundle.putString(ARG_USER, user)
        bundle.putString(ARG_REPO, repo)
        fragment.arguments = bundle
        val activity: FragmentActivity = mvpView.context as FragmentActivity
        activity.fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("search")
                .commit()
    }

    // handle search view events
    override fun getIntent(): Observable<String> {
        return RxSearchView.queryTextChangeEvents(search)
                .filter { it.queryText().count() >= 3 }
                .map { t: SearchViewQueryTextEvent -> t.queryText().toString() }
                .debounce(500, TimeUnit.MILLISECONDS)
    }

    // release resources
    override fun destroy() {
        compositeDisposable.clear()
    }
}

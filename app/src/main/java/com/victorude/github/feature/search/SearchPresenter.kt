package com.victorude.github.feature.search

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
import com.victorude.github.feature.repo.RepoDetailFragment
import com.victorude.github.model.Repo
import com.victorude.github.service.GitHubService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

open class SearchPresenter @Inject constructor() : BasePresenterImpl<String>(),
        SearchResultAdapter.OnItemClickListener {

    @Inject
    lateinit var github: GitHubService
    lateinit var search: SearchView
    private lateinit var resultsView: RecyclerView

    override fun setView(view: View) {
        super.setView(view)

        search = mvpView.findViewById(R.id.search)
        resultsView = mvpView.findViewById(R.id.results)
        resultsView.layoutManager = LinearLayoutManager(mvpView.context)

        val searchIntent: Disposable = getIntent()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .flatMap { queryString ->
                    Observable.fromCallable {
                        github.search(mapOf("q" to queryString)).execute().body()
                    }.observeOn(AndroidSchedulers.mainThread())
                }
                .subscribe({ results ->
                    resultsView.adapter = SearchResultAdapter(results!!, this)
                }, { throwable ->
                    Timber.e(throwable)
                })

        compositeDisposable.add(searchIntent)
    }

    override fun getIntent(): Observable<String> {
        return RxSearchView.queryTextChangeEvents(search)
                .filter { it.queryText().count() >= 3 }
                .map { t: SearchViewQueryTextEvent -> t.queryText().toString() }
                .debounce(500, TimeUnit.MILLISECONDS)
    }

    override fun onItemClick(item: Repo) {
        val fragment = RepoDetailFragment()
        val bundle = Bundle()
        bundle.putParcelable(ARG_REPO, item)
        fragment.arguments = bundle
        val activity: FragmentActivity = mvpView.context as FragmentActivity
        activity.fragmentManager.beginTransaction()
                .replace(R.id.container, fragment, RepoDetailFragment::class.simpleName)
                .addToBackStack("search")
                .commit()
    }
}

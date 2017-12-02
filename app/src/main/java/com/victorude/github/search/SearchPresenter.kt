package com.victorude.github.search

import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.SearchView
import com.jakewharton.rxbinding2.widget.RxSearchView
import com.jakewharton.rxbinding2.widget.SearchViewQueryTextEvent
import com.victorude.github.BasePresenter
import com.victorude.github.R
import com.victorude.github.model.Repo
import com.victorude.github.model.Result
import com.victorude.github.service.GitHubService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

open class SearchPresenter @Inject constructor() : BasePresenter<String> {

    @Inject
    lateinit var github: GitHubService

    lateinit var mvpView: View
    lateinit var search: SearchView
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun setView(view: View) {
        mvpView = view

        this.search = mvpView.findViewById(R.id.search)

        val searchIntent: Disposable = getIntent().startWith("")
                .doOnNext({ queryString ->
                    if (!queryString.isEmpty()) {
                        github.search(mapOf("q" to queryString))
                                .enqueue(object : Callback<Result<List<Repo>>> {
                                    override fun onFailure(call: Call<Result<List<Repo>>>?, t: Throwable?) {
                                        //TODO: update error handling to notify the user
                                        Log.d("TAG", t?.message)
                                    }

                                    override fun onResponse(call: Call<Result<List<Repo>>>?, response: Response<Result<List<Repo>>>?) {
                                        val results: Result<List<Repo>>? = response!!.body()
                                        val resultsView: RecyclerView = mvpView.findViewById(R.id.results)
                                        resultsView.layoutManager = LinearLayoutManager(mvpView.context)
                                        resultsView.adapter = SearchResultAdapter(results!!)
                                    }
                                })
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

        compositeDisposable.add(searchIntent)
    }

    // update model based on user intent here
    fun onFabClick(v: View) {
        // update the view after model changes
        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
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

package com.victorude.github.feature.search.list

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent
import com.victorude.github.BasePresenterImpl
import com.victorude.github.R
import com.victorude.github.common.ARG_REPO
import com.victorude.github.common.DEBOUNCE
import com.victorude.github.feature.search.detail.SearchDetailFragment
import com.victorude.github.model.Repo
import com.victorude.github.model.Result
import com.victorude.github.service.GitHubService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import kotlinx.android.synthetic.main.fragment_search.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

open class SearchPresenter @Inject constructor() : BasePresenterImpl<String>(),
        SearchResultAdapter.OnItemClickListener {

    override fun restoreState(savedInstanceState: Bundle?) {

    }

    override fun saveState(): Bundle? {
        return Bundle().run {
            this
        }
    }

    @Inject
    lateinit var github: GitHubService

    private val history: ReplaySubject<String> = ReplaySubject.create()

    override fun setView(view: View) {
        super.setView(view)

        val activity: FragmentActivity = mvpView.context as FragmentActivity
        fragment.results.addOnScrollListener(SearchOnScrollListener())
        fragment.results.layoutManager = LinearLayoutManager(mvpView.context)

        val searchIntent = PublishSubject.combineLatest(getIntent(), fragment.results.observable,
                BiFunction { query: String, p: Int ->
                    Timber.d("Loading page $p of query = $query")
                    history.onNext(query)
                    getNextPage(query, p)
                })
                .doOnEach {
                    fragment.results.afterLoad
                }
                .concatMap { result -> result }
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ results ->
                    activity.runOnUiThread {
                        if (fragment.results.onFirstPage) {
                            Timber.d("Replacing existing search recyclerview adapter")
                            fragment.results.adapter = SearchResultAdapter(results, this)
                        } else {
                            Timber.d("Adding new items to existing search recyclerview adapter")
                            fragment.results.adapter?.add(results.items)
                        }

                        fragment.results.adapter.let {

                            Timber.d("Loading finished. Displaying ${it?.itemCount}" +
                                    " of ${(it as SearchResultAdapter).getPotentialItemCount()} items")
                        }
                    }
                }, { throwable ->
                    Timber.e(throwable)
                })

        compositeDisposable.add(searchIntent)
    }

    private fun getNextPage(queryString: String, p: Int): Observable<Result<MutableList<Repo>>> {
        return github.searchRepositories(mapOf(
                "code" to getAccessToken(),
                "q" to queryString,
                "page" to p.toString(),
                "per_page" to 100.toString()
        ))
    }

    override fun getIntent(): Observable<String> {
        return RxSearchView.queryTextChangeEvents(fragment.search)
                .filter { it.queryText().count() >= 1 }
                .distinctUntilChanged()
                .map { t: SearchViewQueryTextEvent -> t.queryText().toString() }
                .debounce(DEBOUNCE, TimeUnit.MILLISECONDS)
                .doOnNext {
                    fragment.results.resetPageCount
                }
    }

    override fun onItemClick(item: Repo) {
        val fragment = SearchDetailFragment()
        val bundle = Bundle()
        bundle.putParcelable(ARG_REPO, item)
        fragment.arguments = bundle
        val activity: FragmentActivity = mvpView.context as FragmentActivity
        activity.fragmentManager.beginTransaction()
                .replace(R.id.container, fragment, SearchDetailFragment::class.simpleName)
                .addToBackStack("search")
                .commit()
    }

    override fun getName(): String {
        return SearchPresenter::class.java.simpleName
    }
}

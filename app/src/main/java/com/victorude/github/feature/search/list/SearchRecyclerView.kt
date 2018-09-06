package com.victorude.github.feature.search.list

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.victorude.github.model.Repo
import com.victorude.github.model.Result
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class SearchRecyclerView
    : RecyclerView {

    constructor(
            context: Context) : super(context)

    constructor(
            context: Context,
            attrs: AttributeSet) : super(context, attrs)

    constructor(
            context: Context,
            attrs: AttributeSet,
            defStyle: Int) : super(context, attrs, defStyle)


    private val page: BehaviorSubject<Int> = BehaviorSubject.create()

    init {
        page.onNext(1)
    }

    var adapter: SearchResultAdapter?
        get() = try {
            (getAdapter() as SearchResultAdapter)
        } catch (e: TypeCastException) {
            Timber.e(e.cause)
            defaultAdapter
        }
        set(value) = setAdapter(value)
    val afterLoad: Unit?
        get() = afterLoad()
    val onFirstPage: Boolean
        get() = currentPage == 1
    val resetPageCount: Unit?
        get() = resetPageCount()
    val pageAvailable: Boolean
        get() = isViewItemThresholdReached() && adapter?.isItemCountMet() ?: true && isPageCountMet()
    val observable: Observable<Int>
        get() = page.observeOn(Schedulers.io()).distinctUntilChanged()
    val loadNextPage: Unit
        get() {
            loading = true
            page.onNext(page.value + 1)
        }
    var loading: Boolean = false
        private set(value) {
            field = value
            Timber.d("loading = $value")
        }

    private val visibleThreshold: Int = 25
    private val linearLayoutManager: LinearLayoutManager
        get() = layoutManager as LinearLayoutManager
    private val currentPage: Int
        get() = page.value

    private fun isViewItemThresholdReached(): Boolean {
        return (linearLayoutManager.findLastVisibleItemPosition() + visibleThreshold) >=
                (adapter?.itemCount ?: 0-1) // -1 to make count 0 indexed
    }

    private fun isPageCountMet(): Boolean {
        val potentialItemCount = adapter?.getPotentialItemCount() ?: 0
        val fullPageCount = potentialItemCount / 100
        val partialPageCount = if ((potentialItemCount % 100) > 0) 1 else 0
        return page.value < (fullPageCount + partialPageCount)
    }

    private fun afterLoad() {
        loading = false
    }

    private fun resetPageCount() {
        page.onNext(1)
    }

    private val defaultAdapter = SearchResultAdapter(
            Result(0, true, mutableListOf()), object : SearchResultAdapter.OnItemClickListener {
        override fun onItemClick(item: Repo) {

        }
    })
}

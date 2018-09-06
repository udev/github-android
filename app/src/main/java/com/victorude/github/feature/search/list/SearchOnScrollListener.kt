package com.victorude.github.feature.search.list

import android.support.v7.widget.RecyclerView

class SearchOnScrollListener : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val searchRecyclerView: SearchRecyclerView = recyclerView as SearchRecyclerView
        if (!searchRecyclerView.loading && searchRecyclerView.pageAvailable) {
            searchRecyclerView.loadNextPage
        }
    }
}
package com.victorude.github.feature.search

abstract class SearchItemClickListener {
    abstract fun onItemClick(user: String, repo: String)
}

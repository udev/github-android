package com.victorude.github.search

abstract class SearchItemClickListener {
    abstract fun onItemClick(user: String, repo: String)
}

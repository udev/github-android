package com.victorude.github.feature.search.list

import com.victorude.github.BasePresenter
import com.victorude.github.MvpFragment
import com.victorude.github.R
import javax.inject.Inject

class SearchFragment : MvpFragment() {

    @Inject
    lateinit var presenter: SearchPresenter

    override fun getLayout(): Int {
        return R.layout.fragment_search
    }

    override fun getPresenter(): BasePresenter {
        return presenter
    }

    override fun getTitle(): String {
        return getString(R.string.fragment_search_title)
    }
}

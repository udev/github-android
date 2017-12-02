package com.victorude.github.search

import com.victorude.github.BasePresenter
import com.victorude.github.MvpFragment
import com.victorude.github.R
import javax.inject.Inject

class SearchFragment : MvpFragment() {

    @Inject lateinit var presenter: SearchPresenter

    override fun getPresenter(): BasePresenter {
        return presenter
    }

    override fun getLayout(): Int {
        return R.layout.fragment_search
    }
}

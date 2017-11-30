package com.victorude.github.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victorude.github.R
import kotlinx.android.synthetic.main.content_search.*
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment: Fragment() {

    // uninitialized presenter
    private var presenter: SearchPresenter? = null

    // inflate the fragment view
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize the presenter only after the view is successfully created
        presenter = SearchPresenter(view!!, search)

        // when the floating action button is clicked
        fab.setOnClickListener { button ->
            // the presenter updates the model
            presenter?.onFabClick(button)
        }
    }

    override fun onStop() {
        super.onStop()

        // release presenter resources
        presenter?.destroy()
    }
}

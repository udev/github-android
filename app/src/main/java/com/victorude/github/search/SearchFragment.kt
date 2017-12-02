package com.victorude.github.search

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victorude.github.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var presenter: SearchPresenter

    @SuppressLint("CheckResult")
    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }

    // inflate the fragment view
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set the view
        presenter.setView(view!!)

        // when the floating action button is clicked
        fab.setOnClickListener { button ->
            // the presenter updates the model
            presenter.onFabClick(button)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // release presenter resources
        presenter.destroy()
    }
}

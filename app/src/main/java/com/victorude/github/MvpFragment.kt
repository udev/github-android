package com.victorude.github

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.AndroidInjection
import org.jetbrains.anko.find

abstract class MvpFragment : Fragment() {

    @SuppressLint("CheckResult")
    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set title
        val toolbar = activity.find<CollapsingToolbarLayout>(R.id.toolbar_layout)
        toolbar.title = getTitle()

        // set the view
        getPresenter().setView(view!!)
    }

    // inflate the fragment view
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(getLayout(), container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // release getPresenter resources
        getPresenter().destroy()
    }

    protected abstract fun getPresenter(): BasePresenter
    protected abstract fun getLayout(): Int
    protected abstract fun getTitle(): String
}

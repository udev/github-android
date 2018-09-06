package com.victorude.github

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_container_view.*

abstract class MvpFragment : Fragment() {

    @SuppressLint("CheckResult")
    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }

    // inflate the fragment view
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set title
        val toolbar = activity.toolbar_layout
        toolbar.title = getTitle()

        getPresenter().run {
            fragment = this@MvpFragment
            restoreState(arguments)
            setView(view!!)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putBundle(getPresenter().getName(), getPresenter().saveState())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // release getPresenter resources
        getPresenter().destroy()
    }

    protected abstract fun getPresenter(): BasePresenter
    protected abstract fun getLayout(): Int
    protected abstract fun getTitle(): String
    companion object {
        private val STATE_PRESENTER: String = "${MvpFragment::class.java.simpleName}.STATE_PRESENTER"
    }
}

package com.victorude.github

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.AndroidInjection

abstract class MvpFragment : Fragment() {

    @SuppressLint("CheckResult")
    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    companion object {
        inline fun <reified T> getFragment(context: Context): T {
            return (context as Activity).fragmentManager
                    .findFragmentByTag(T::class.simpleName) as T
        }
    }

    protected abstract fun getPresenter(): BasePresenter
    protected abstract fun getLayout(): Int
}

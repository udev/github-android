package com.victorude.github

import android.view.View
import io.reactivex.Observable

abstract class BasePresenterImpl<T> : BasePresenter {
    lateinit var mvpView: View

    override fun setView(view: View) {
        mvpView = view
    }

    abstract fun getIntent(): Observable<T>
}

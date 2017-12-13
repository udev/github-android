package com.victorude.github

import android.view.View
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenterImpl<T> : BasePresenter {
    lateinit var mvpView: View
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun setView(view: View) {
        mvpView = view
    }

    abstract fun getIntent(): Observable<T>

    // release resources
    override fun destroy() {
        compositeDisposable.clear()
    }
}

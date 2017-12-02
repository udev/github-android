package com.victorude.github

import android.view.View
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

interface BasePresenter<T> {
    val compositeDisposable: CompositeDisposable

    fun setView(view: View)
    fun getIntent(): Observable<T>
    fun destroy()
}

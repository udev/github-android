package com.victorude.github

import android.view.View
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

interface BasePresenter<T> {
    var mvpView: View
    val compositeDisposable: CompositeDisposable

    fun getIntent(): Observable<T>
    fun destroy()
}
package com.victorude.github

import android.os.Bundle
import android.view.View
import io.reactivex.disposables.CompositeDisposable

interface BasePresenter {
    val compositeDisposable: CompositeDisposable
    var fragment: MvpFragment
    var mvpView: View

    fun setView(view: View)
    fun saveState(): Bundle?
    fun restoreState(savedInstanceState: Bundle?)
    fun getName(): String
    fun destroy()
    fun setAccessCode(code: String)
    fun getAccessCode(): String
    fun invalidateAccessCode()
}

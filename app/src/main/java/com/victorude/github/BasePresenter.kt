package com.victorude.github

import android.view.View
import io.reactivex.disposables.CompositeDisposable

interface BasePresenter {
    val compositeDisposable: CompositeDisposable

    fun setView(view: View)
    fun destroy()
}

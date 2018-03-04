package com.victorude.github

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
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

    companion object {
        @JvmStatic
        @BindingAdapter("bind:imageUrl", "bind:error")
        fun loadImage(view: ImageView, url: String, error: Drawable) {
            Picasso.with(view.context).load(url).error(error).into(view)
        }
    }
}

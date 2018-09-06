package com.victorude.github

import android.content.Context.MODE_PRIVATE
import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenterImpl<T> : BasePresenter {
    override lateinit var mvpView: View
    override lateinit var fragment: MvpFragment
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun setView(view: View) {
        mvpView = view
    }

    abstract override fun restoreState(savedInstanceState: Bundle?)
    abstract override fun saveState(): Bundle?
    abstract fun getIntent(): Observable<T>

    // release resources
    override fun destroy() {
        compositeDisposable.clear()
    }

    final override fun setAccessCode(code: String) {
        mvpView.context.getSharedPreferences(mvpView.context.packageName, MODE_PRIVATE).run {
            edit().putString(PREF_ACCESS_CODE, code).apply()
        }
    }

    final override fun getAccessCode(): String {
        return mvpView.context.getSharedPreferences(mvpView.context.packageName, MODE_PRIVATE).run {
            getString(PREF_ACCESS_CODE, "")
        }
    }

    final override fun invalidateAccessCode() {
        mvpView.context.getSharedPreferences(mvpView.context.packageName, MODE_PRIVATE).run {
            edit().putString(PREF_ACCESS_CODE, "").apply()
        }
    }

    companion object {

        val PREF_ACCESS_CODE: String = "${BasePresenterImpl::class.java.simpleName}.PREF_ACCESS_CODE"

        @JvmStatic
        @BindingAdapter("bind:imageUrl", "bind:error")
        fun loadImage(view: ImageView, url: String, error: Drawable) {
            Picasso.with(view.context).load(url).error(error).into(view)
        }
    }
}

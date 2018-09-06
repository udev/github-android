package com.victorude.github.feature.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import com.victorude.github.BasePresenterImpl
import com.victorude.github.R
import com.victorude.github.common.DEBOUNCE
import com.victorude.github.feature.search.list.SearchFragment
import com.victorude.github.service.GitHubService
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.fragment_auth.*
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthPresenter @Inject constructor() : BasePresenterImpl<String>() {

    @Inject
    lateinit var github: GitHubService
    private val state: BehaviorSubject<String> = BehaviorSubject.create()

    override fun restoreState(savedInstanceState: Bundle?) {
        savedInstanceState?.run {
            state.onNext(getString(STATE_UUID))
        }
    }

    override fun saveState(): Bundle? {
        return Bundle().run {
            putString(STATE_UUID, state.value ?: "")
            this
        }
    }

    override fun setView(view: View) {
        super.setView(view)

        getAccessCode().takeIf { !it.isEmpty() }.run { goToSearch() }

        fragment.activity.intent?.data.run {
            val code = this?.getQueryParameter("code")
            this?.getQueryParameter("state").let {
                if (!it.isNullOrEmpty() && state.value == it) {
                    Timber.d("GitHub OAuth2 grant successful")
                    setAccessCode(code!!)
                    goToSearch()
                }
            }
        }

        val loginIntent: Disposable = getIntent()
                .map { state ->
                    openWebPage(state)
                }
                .subscribe({ response ->
                    Timber.d("authentication response:\n$response.")
                }, { throwable: Throwable? ->
                    Timber.e(throwable)
                })

        compositeDisposable.add(loginIntent)
    }

    private fun goToSearch() {
        val searchFragment = SearchFragment()
        fragment.fragmentManager.beginTransaction()
                .replace(R.id.container, searchFragment, SearchFragment::class.simpleName)
                .commit()
    }

    override fun getIntent(): Observable<String> {
        return RxView.clicks(fragment.auth_button_login)
                .throttleLast(DEBOUNCE, TimeUnit.MILLISECONDS)
                .map { UUID.randomUUID().toString() }
                .doOnNext { s ->
                    state.onNext(s)
                }
    }

    fun openWebPage(state: String) {
        val webpage = Uri.parse("https://github.com/login/oauth/authorize?client_id=817fae93ce823a0c0bf4&redirect_uri=udev://com.victorude.github&scope=repo:read&state=$state&allow_signup=true")
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(fragment.activity.packageManager) != null) {
            startActivity(mvpView.context, intent, null)
        }
    }

    override fun getName(): String {
        return AuthPresenter::class.java.simpleName
    }

    companion object {
        val STATE_UUID: String = "${AuthPresenter::class.java.simpleName}.STATE_UUID"
    }
}

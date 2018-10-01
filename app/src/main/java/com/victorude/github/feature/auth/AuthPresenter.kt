package com.victorude.github.feature.auth

import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import com.victorude.github.BasePresenterImpl
import com.victorude.github.R
import com.victorude.github.common.DEBOUNCE
import com.victorude.github.feature.search.list.SearchFragment
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.fragment_auth.*
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthPresenter @Inject constructor() : BasePresenterImpl<String>() {

//    @Inject
//    lateinit var apolloClient: ApolloClient

    private val state: BehaviorSubject<String> = BehaviorSubject.create()

    override fun setView(view: View) {
        super.setView(view)

//        goToSearchIfTokenIsValid(getAccessToken())

        fragment.auth_button_go.setOnClickListener {
            fragment.auth_access_token.text?.toString()?.let { token ->
//                goToSearchIfTokenIsValid(token)
                goToSearch()
            }
        }

        val loginIntent: Disposable = getIntent()
                .map { state ->
                    openWebPage(state)
                }
                .subscribe({
                    Timber.d("Creating a new personal access token")
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

    private val scheme: String = "https"
    private val base_url: String = "github.com"
    private val path: String = "/settings/tokens/new"
    private val scopes: String = "user,public_repo,repo,repo_deployment,repo:status,read:repo_hook,read:org,read:public_key,read:gpg_key"

    private fun openWebPage(state: String) {
        val applicationInfo = mvpView.context.applicationInfo
        val name = mvpView.context.packageManager.getApplicationLabel(applicationInfo)
        val webpage = Uri.parse("$scheme://$base_url$path?scopes=$scopes&description=$name")
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(fragment.activity.packageManager) != null) {
            startActivity(mvpView.context, intent, null)
        }
    }

//    private fun goToSearchIfTokenIsValid(token: String) {
//        val query: LoginUserQuery = LoginUserQuery.builder().build()
//        val call = apolloClient.query(query)
//        val response = Rx2Apollo.from(call)
//                .subscribe({ response ->
//                    Timber.d("fresh!" + response.data()?.toString())
//                    setAccessToken(token)
//                    goToSearch()
//                }, {
//                    Timber.e(it)
//                })
//        compositeDisposable.add(response)
//    }

    override fun getName(): String {
        return AuthPresenter::class.java.simpleName
    }
}

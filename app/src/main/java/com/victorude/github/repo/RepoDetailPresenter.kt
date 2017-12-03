package com.victorude.github.repo

import android.view.View
import com.victorude.github.BasePresenterImpl
import com.victorude.github.model.Repo
import com.victorude.github.service.GitHubService
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class RepoDetailPresenter @Inject constructor() : BasePresenterImpl<Repo>() {

    @Inject
    lateinit var github: GitHubService
    var user: String = ""
    var repo: String = ""
    var details: Repo? = null
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun setView(view: View) {
        super.setView(view)

        //TODO: get "owner" and "repo" from intent/arguments
        github.repo(user, repo)
                .enqueue(object : Callback<Repo> {
                    override fun onFailure(call: Call<Repo>?, t: Throwable?) {
                        //TODO: update error handling to notify the user
                        Timber.tag("TAG").d(t?.message, t)
                    }

                    override fun onResponse(call: Call<Repo>?, response: Response<Repo>?) {
                        details = response?.body()
                    }
                })
    }

    override fun destroy() {
        compositeDisposable.clear()
    }

    override fun getIntent(): Observable<Repo> {
        return Observable.just(details)
    }
}

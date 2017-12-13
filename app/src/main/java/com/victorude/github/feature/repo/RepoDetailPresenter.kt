package com.victorude.github.feature.repo

import android.view.View
import com.victorude.github.BasePresenterImpl
import com.victorude.github.MvpFragment
import com.victorude.github.model.Repo
import com.victorude.github.service.GitHubService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class RepoDetailPresenter @Inject constructor() : BasePresenterImpl<Repo>() {

    @Inject
    lateinit var github: GitHubService
    var user: String? = null
    var repo: String? = null
    private var details: Repo? = null

    override fun setView(view: View) {
        super.setView(view)

        val intent = getIntent()
                .skipWhile { repo == null }
                .subscribe({ repo ->
                    details = repo
                    val fragment: RepoDetailFragment = MvpFragment.getFragment(mvpView.context)
                    fragment.bind(details!!)
                }, { throwable ->
                    Timber.e(throwable)
                })

        compositeDisposable.add(intent)
    }

    override fun getIntent(): Observable<Repo> {
        return Observable.fromCallable<Repo>({
            github.repo(user, repo).execute().body()
        }).subscribeOn(Schedulers.io())
    }
}

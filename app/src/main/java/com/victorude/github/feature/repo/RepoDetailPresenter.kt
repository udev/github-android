package com.victorude.github.feature.repo

import com.victorude.github.BasePresenterImpl
import com.victorude.github.model.Repo
import com.victorude.github.service.GitHubService
import io.reactivex.Observable
import javax.inject.Inject

class RepoDetailPresenter @Inject constructor() : BasePresenterImpl<Repo>() {

    @Inject
    lateinit var github: GitHubService
    lateinit var repo: Repo

    fun refresh() {
        throw NotImplementedError("TODO: add detail view refresh")
    }

    override fun getIntent(): Observable<Repo> {
        val args = repo.full_name.split('/')
        return Observable.fromCallable<Repo>({
            github.repo(args[0], args[1]).execute().body()
        }).defaultIfEmpty(repo)
    }
}

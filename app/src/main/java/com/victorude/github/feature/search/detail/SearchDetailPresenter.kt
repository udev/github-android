package com.victorude.github.feature.search.detail

import android.os.Bundle
import com.victorude.github.BasePresenterImpl
import com.victorude.github.model.Repo
import io.reactivex.Observable
import javax.inject.Inject

class SearchDetailPresenter @Inject constructor() : BasePresenterImpl<Repo>() {

    override fun restoreState(savedInstanceState: Bundle?) {

    }

    override fun saveState(): Bundle? {
        return Bundle().run {
            this
        }
    }

//    @Inject
//    lateinit var github: GitHubService
    lateinit var repo: Repo

    fun refresh() {
        throw NotImplementedError("TODO: add detail view refresh")
    }

    override fun getIntent(): Observable<Repo> {
        val args = repo.full_name.split('/')
//        return github.repo(args[0], args[1])
        return Observable.empty()
    }

    override fun getName(): String {
        return SearchDetailPresenter::class.java.simpleName
    }
}

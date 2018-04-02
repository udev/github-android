package com.victorude.github.feature.repo

import com.victorude.github.BasePresenterImpl
import com.victorude.github.model.GiphyData
import com.victorude.github.service.GitHubService
import io.reactivex.Observable
import javax.inject.Inject

class RepoDetailPresenter @Inject constructor() : BasePresenterImpl<GiphyData>() {

    @Inject
    lateinit var github: GitHubService
    lateinit var data: GiphyData

    fun refresh() {
        throw NotImplementedError("TODO: add detail view refresh")
    }

    override fun getIntent(): Observable<GiphyData> {
        return Observable.just(data)
    }
}

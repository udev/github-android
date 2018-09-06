package com.victorude.github.service

import com.victorude.github.model.Repo
import com.victorude.github.model.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface GitHubService {

    @GET("search/repositories")
    fun searchRepositories(@QueryMap options: Map<String, String>): Observable<Result<MutableList<Repo>>>

    @GET("repos/{owner}/{repo}")
    fun repo(@Path("owner") owner: String?, @Path("repo") repo: String?): Observable<Repo>
}

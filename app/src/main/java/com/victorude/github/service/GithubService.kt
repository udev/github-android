package com.victorude.github.service

import com.victorude.github.model.Repo
import com.victorude.github.model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface GitHubService {
    @GET("search/repositories")
    fun search(@QueryMap options: Map<String, String>): Call<Result<List<Repo>>>

    @GET("repos/{owner}/{repo}")
    fun repo(@Path("owner") owner: String, @Path("repo") repo: String): Call<Repo>
}

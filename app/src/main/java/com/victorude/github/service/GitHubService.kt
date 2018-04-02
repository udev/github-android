package com.victorude.github.service

import com.victorude.github.model.GiphyResponse
import com.victorude.github.model.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

const val API_KEY: String = "GsJSKEvGGFN7s8OuUIvvTW1pjnAT05QW"

interface GitHubService {

    @GET("v1/gifs/search")
    fun search(@Query("api_key") apiKey: String, @QueryMap options: Map<String, String>): Call<GiphyResponse>

    @GET("repos/{owner}/{repo}")
    fun repo(@Path("owner") owner: String?, @Path("repo") repo: String?): Call<Repo>
}

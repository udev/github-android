package com.victorude.github.service

import com.victorude.github.model.Repo
import com.victorude.github.model.Result
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GitHubService {
    @GET("search/repositories")
    fun search(@QueryMap options: Map<String, String>): Call<Result<List<Repo>>>

    companion object {
        private var retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        var service: GitHubService = retrofit.create(GitHubService::class.java)
    }
}

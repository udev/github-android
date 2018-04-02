package com.victorude.github.di.github

import com.victorude.github.service.GitHubService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class GithubModule {
    @Provides
    @Singleton
    fun provideGitHub(okHttpClient: OkHttpClient): GitHubService {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.giphy.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

        return retrofit.create(GitHubService::class.java)
    }
}

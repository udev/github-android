package com.victorude.github.di.github

import com.victorude.github.di.okhttp.OkHttpModule
import com.victorude.github.service.GitHubService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GithubModule::class, OkHttpModule::class])
interface GithubComponent {
    fun gitHubService(): GitHubService
}

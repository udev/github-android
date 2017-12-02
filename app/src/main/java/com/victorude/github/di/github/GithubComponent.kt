package com.victorude.github.di.github

import com.victorude.github.service.GitHubService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GithubModule::class])
interface GithubComponent {
    fun gitHubService(): GitHubService
}

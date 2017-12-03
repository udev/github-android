package com.victorude.github.di.search

import com.victorude.github.di.github.GithubModule
import com.victorude.github.repo.RepoDetailFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [GithubModule::class])
interface RepoDetailFragmentSubcomponent : AndroidInjector<RepoDetailFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<RepoDetailFragment>()
}

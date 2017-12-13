package com.victorude.github.di.github

import com.victorude.github.feature.repo.RepoDetailFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [GithubModule::class])
interface RepoDetailFragmentSubcomponent : AndroidInjector<RepoDetailFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<RepoDetailFragment>()
}

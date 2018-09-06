package com.victorude.github.di.github

import com.victorude.github.feature.search.detail.SearchDetailFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [GithubModule::class])
interface RepoDetailFragmentSubcomponent : AndroidInjector<SearchDetailFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SearchDetailFragment>()
}

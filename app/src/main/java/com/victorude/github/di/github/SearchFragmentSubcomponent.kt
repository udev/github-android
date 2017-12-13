package com.victorude.github.di.github

import com.victorude.github.feature.search.SearchFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [GithubModule::class])
interface SearchFragmentSubcomponent : AndroidInjector<SearchFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SearchFragment>()
}

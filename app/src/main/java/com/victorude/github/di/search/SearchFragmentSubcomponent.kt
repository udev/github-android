package com.victorude.github.di.search

import com.victorude.github.di.github.GithubModule
import com.victorude.github.search.SearchFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [GithubModule::class])
interface SearchFragmentSubcomponent: AndroidInjector<SearchFragment> {
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<SearchFragment>()
}

package com.victorude.github.di.github

import android.app.Fragment
import com.victorude.github.feature.search.detail.SearchDetailFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.FragmentKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [RepoDetailFragmentSubcomponent::class])
abstract class RepoDetailFragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(SearchDetailFragment::class)
    abstract fun bindRepoDetailFragmentInjectorFactory(builder: RepoDetailFragmentSubcomponent.Builder)
            : AndroidInjector.Factory<out Fragment>
}

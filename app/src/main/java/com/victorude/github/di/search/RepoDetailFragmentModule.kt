package com.victorude.github.di.search

import android.app.Fragment
import com.victorude.github.repo.RepoDetailFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.FragmentKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [RepoDetailFragmentSubcomponent::class])
abstract class RepoDetailFragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(RepoDetailFragment::class)
    abstract fun bindRepoDetailFragmentInjectorFactory(builder: RepoDetailFragmentSubcomponent.Builder)
            : AndroidInjector.Factory<out Fragment>
}

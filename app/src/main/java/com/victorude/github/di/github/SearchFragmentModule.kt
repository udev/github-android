package com.victorude.github.di.github

import android.app.Fragment
import com.victorude.github.feature.search.SearchFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.FragmentKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [SearchFragmentSubcomponent::class])
abstract class SearchFragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(SearchFragment::class)
    abstract fun bindSearchFragmentInjectorFactory(builder: SearchFragmentSubcomponent.Builder)
            : AndroidInjector.Factory<out Fragment>
}

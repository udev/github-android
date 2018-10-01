package com.victorude.github.di.github

import com.victorude.github.di.apollo.ApolloModule
import com.victorude.github.feature.auth.AuthFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Subcomponent(modules = [ApolloModule::class])
interface AuthFragmentSubcomponent : AndroidInjector<AuthFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<AuthFragment>()
}

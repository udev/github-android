package com.victorude.github.di.apollo

import com.apollographql.apollo.ApolloClient
import com.victorude.github.di.okhttp.OkHttpModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApolloModule::class, OkHttpModule::class])
interface ApolloComponent {
    fun apolloClient(): ApolloClient
}

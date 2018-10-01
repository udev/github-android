package com.victorude.github.di.apollo

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class ApolloModule {
    @Provides
    @Singleton
    fun providesApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder()
                .serverUrl("https://api.github.com/graphql")
                .okHttpClient(okHttpClient)
                .build()
    }
}

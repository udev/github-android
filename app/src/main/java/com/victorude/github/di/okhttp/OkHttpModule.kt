package com.victorude.github.di.okhttp

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class OkHttpModule {
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(OkHttpInterceptor())
                .build()
    }
}

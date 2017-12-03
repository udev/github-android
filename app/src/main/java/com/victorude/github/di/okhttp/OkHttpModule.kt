package com.victorude.github.di.okhttp

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber

@Module
class OkHttpModule {
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val request: Request = chain.request()
            Timber.d(request.toString())
            val response: Response = chain.proceed(request)
            Timber.d(response.toString())
            response
        }
        return httpClient.build()
    }
}

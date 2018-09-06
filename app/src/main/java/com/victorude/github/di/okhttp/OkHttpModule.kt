package com.victorude.github.di.okhttp

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import timber.log.Timber

@Module
class OkHttpModule {
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->

            val request = chain.request()
            request.newBuilder()
                    .header("User-Agent", "udev/github-android;dev 0.0.1")

            val t1 = System.nanoTime()
            Timber.tag("OkHttpClient").d("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers())

            val response = chain.proceed(request)

            val t2 = System.nanoTime()
            Timber.tag("OkHttpClient").d("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6, response.headers())

            response
        }
        return httpClient.build()
    }
}

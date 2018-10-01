package com.victorude.github.di.okhttp

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OkHttpInterceptor @Inject constructor() : Interceptor {
    private var sessionToken: String = ""

    fun setSessionToken(sessionToken: String) {
        this.sessionToken = sessionToken
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestBuilder = request.newBuilder()

        if (request.header(NO_AUTH_HEADER_KEY) == null) {
            // needs credentials
            if (sessionToken.isEmpty()) {
                throw RuntimeException("Session token should be defined for auth apis")
            } else {
                requestBuilder.addHeader("Cookie", sessionToken)
            }
        }

        request.newBuilder()
                .header("User-Agent", "udev/github-android;dev 0.0.1")

        val t1 = System.nanoTime()
        Timber.tag("OkHttpClient").d("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers())

        val response = chain.proceed(request)

        val t2 = System.nanoTime()
        Timber.tag("OkHttpClient").d("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6, response.headers())


        return chain.proceed(requestBuilder.build())
    }

    companion object {
        const val NO_AUTH_HEADER_KEY = "Authorization"
    }
}

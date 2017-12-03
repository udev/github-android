package com.victorude.github.di.okhttp

import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(modules = [OkHttpModule::class])
interface OkHttpComponent {
    fun okHttpClient(): OkHttpClient
}

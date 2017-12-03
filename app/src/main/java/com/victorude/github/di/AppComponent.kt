package com.victorude.github.di

import com.victorude.github.di.main.MainActivityModule
import com.victorude.github.di.okhttp.OkHttpModule
import com.victorude.github.di.search.RepoDetailFragmentModule
import com.victorude.github.di.search.SearchFragmentModule
import dagger.Component

@Component(modules = [
    MainActivityModule::class,
    SearchFragmentModule::class,
    RepoDetailFragmentModule::class,
    OkHttpModule::class
])
interface AppComponent {
    fun inject(app: App)
}

package com.victorude.github.di

import android.app.Activity
import android.app.Application
import com.victorude.github.BuildConfig
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

class App: Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.create()
                .inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector
}

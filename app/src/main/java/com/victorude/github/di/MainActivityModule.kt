package com.victorude.github.di

import android.app.Activity
import com.victorude.github.MainActivity
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.android.ActivityKey
import dagger.multibindings.IntoMap
import dagger.android.AndroidInjector

@Module(subcomponents = [MainActivitySubcomponent::class])
abstract class MainActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(builder: MainActivitySubcomponent.Builder)
            : AndroidInjector.Factory<out Activity>
}

@Component(modules = [MainActivityModule::class])
interface AppComponent {
    fun inject(app: App)
}

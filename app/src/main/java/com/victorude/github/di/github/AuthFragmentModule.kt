package com.victorude.github.di.github

import android.app.Fragment
import com.victorude.github.feature.auth.AuthFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.FragmentKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [AuthFragmentSubcomponent::class])
abstract class AuthFragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(AuthFragment::class)
    abstract fun bindAuthFragmentInjectorFactory(builder: AuthFragmentSubcomponent.Builder)
            : AndroidInjector.Factory<out Fragment>
}

package com.victorude.github

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.victorude.github.feature.auth.AuthFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import javax.inject.Inject

class MainActivity : FragmentActivity(), HasFragmentInjector {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container_view)

        if (savedInstanceState == null) {
            val authFragment = AuthFragment()
            fragmentManager.beginTransaction()
                    .add(R.id.container, authFragment, AuthFragment::class.simpleName).commit()
        }
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> {
        return fragmentInjector
    }
}

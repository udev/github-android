package com.victorude.github

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.FrameLayout
import com.victorude.github.search.SearchFragment
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
        setContentView(R.layout.container_view)

        if (findViewById<FrameLayout>(R.id.container) != null) {
            if (savedInstanceState != null) {
                return
            }

            val searchFragment = SearchFragment()
            searchFragment.arguments = intent.extras
            fragmentManager.beginTransaction()
                    .add(R.id.container, searchFragment).commit()
        }
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> {
        return fragmentInjector
    }
}

package com.victorude.github

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.widget.FrameLayout
import com.victorude.github.search.SearchFragment
import dagger.android.AndroidInjection

class MainActivity : FragmentActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container_view)

        if (findViewById<FrameLayout>(R.id.container) != null) {
            if (savedInstanceState != null) {
                return
            }

            val searchFragment: Fragment = SearchFragment()
            searchFragment.arguments = intent.extras
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, searchFragment).commit()
        }
    }
}

package com.victorude.github.feature.auth

import com.victorude.github.BasePresenter
import com.victorude.github.MvpFragment
import com.victorude.github.R
import javax.inject.Inject

class AuthFragment : MvpFragment() {

    @Inject
    lateinit var presenter: AuthPresenter

    override fun getLayout(): Int {
        return R.layout.fragment_auth
    }

    override fun getPresenter(): BasePresenter {
        return presenter
    }

    override fun getTitle(): String {
        return getString(R.string.fragment_auth_title)
    }
}

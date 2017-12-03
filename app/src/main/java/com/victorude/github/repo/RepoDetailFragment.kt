package com.victorude.github.repo

import android.content.Context
import com.victorude.github.BasePresenter
import com.victorude.github.MvpFragment
import com.victorude.github.R
import com.victorude.github.common.ARG_REPO
import com.victorude.github.common.ARG_USER
import javax.inject.Inject

class RepoDetailFragment : MvpFragment() {

    @Inject lateinit var presenter: RepoDetailPresenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter.user = arguments.getString(ARG_USER)
        presenter.repo = arguments.getString(ARG_REPO)
    }

    override fun getPresenter(): BasePresenter {
        return presenter
    }

    override fun getLayout(): Int {
        return R.layout.fragment_repo_detail
    }
}

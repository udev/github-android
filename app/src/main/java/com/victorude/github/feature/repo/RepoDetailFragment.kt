package com.victorude.github.feature.repo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victorude.github.BasePresenter
import com.victorude.github.MvpFragment
import com.victorude.github.R
import com.victorude.github.common.ARG_REPO
import com.victorude.github.common.ARG_USER
import com.victorude.github.databinding.FragmentRepoDetailBinding
import com.victorude.github.model.Repo
import javax.inject.Inject

class RepoDetailFragment : MvpFragment() {

    @Inject lateinit var presenter: RepoDetailPresenter

    var binding: FragmentRepoDetailBinding? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter.user = arguments.getString(ARG_USER)
        presenter.repo = arguments.getString(ARG_REPO)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentRepoDetailBinding.inflate(inflater!!, container, false)
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.unbind()
    }

    override fun getPresenter(): BasePresenter {
        return presenter
    }

    override fun getLayout(): Int {
        return R.layout.fragment_repo_detail
    }

    fun bind(repo: Repo) {
        binding?.repo = repo
        binding?.executePendingBindings()
    }
}

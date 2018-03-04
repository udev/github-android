package com.victorude.github.feature.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victorude.github.BasePresenter
import com.victorude.github.MvpFragment
import com.victorude.github.R
import com.victorude.github.common.ARG_REPO
import com.victorude.github.databinding.FragmentRepoDetailBinding
import com.victorude.github.model.Repo
import javax.inject.Inject

class RepoDetailFragment : MvpFragment() {

    @Inject lateinit var presenter: RepoDetailPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val repo: Repo = arguments.getParcelable(ARG_REPO)
        val binding = FragmentRepoDetailBinding.inflate(inflater!!, container, false)
        val view = binding.root
        presenter.repo = repo
        binding.repo = presenter.repo
        return view
    }

    override fun getPresenter(): BasePresenter {
        return presenter
    }

    override fun getLayout(): Int {
        return R.layout.fragment_repo_detail
    }
}

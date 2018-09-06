package com.victorude.github.feature.search.detail

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

class SearchDetailFragment : MvpFragment() {

    @Inject
    lateinit var presenter: SearchDetailPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val repo: Repo = arguments.getParcelable(ARG_REPO)
        val binding = FragmentRepoDetailBinding.inflate(inflater!!, container, false)
        val view = binding.root
        presenter.repo = repo
        binding.repo = presenter.repo
        return view
    }

    override fun getLayout(): Int {
        return R.layout.fragment_repo_detail
    }

    override fun getPresenter(): BasePresenter {
        return presenter
    }

    override fun getTitle(): String {
        val repo: Repo = arguments.getParcelable(ARG_REPO)
        return repo.full_name
    }
}

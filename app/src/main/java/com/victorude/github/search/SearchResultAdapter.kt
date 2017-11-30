package com.victorude.github.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.victorude.github.databinding.ResultRepoItemBinding
import com.victorude.github.model.Repo
import com.victorude.github.model.Result

class SearchResultAdapter(private val results: Result<List<Repo>>) : RecyclerView.Adapter<SearchResultAdapter.Companion.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo: Repo = results.items[position]
        holder.bind(repo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ResultRepoItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return results.items.size
    }

    companion object {
        class ViewHolder(private val binding: ResultRepoItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(repo: Repo) {
                binding.repo = repo
                binding.executePendingBindings()
            }
        }
    }
}
package com.victorude.github.feature.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.victorude.github.databinding.ResultRepoItemBinding
import com.victorude.github.model.GiphyData

class SearchResultAdapter(private val results: Array<GiphyData>,
                          private val listener: OnItemClickListener)
    : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: GiphyData)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo: GiphyData = results[position]
        holder.bind(repo)
        holder.itemView.tag = repo
        holder.itemView.setOnClickListener { listener.onItemClick(repo) }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ResultRepoItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    class ViewHolder(private val binding: ResultRepoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GiphyData) {
            binding.data = data
            binding.executePendingBindings()
        }

        fun unbind() {
            binding.unbind()
        }
    }
}

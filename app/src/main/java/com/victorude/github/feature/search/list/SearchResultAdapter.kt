package com.victorude.github.feature.search.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.victorude.github.databinding.ResultRepoItemBinding
import com.victorude.github.model.Repo
import com.victorude.github.model.Result

class SearchResultAdapter(private val results: Result<MutableList<Repo>>,
                          private val listener: OnItemClickListener)
    : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Repo)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo: Repo = results.items[position]
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
        return results.items.size
    }

    fun getPotentialItemCount(): Int {
        return results.total_count
    }

    fun add(items: List<Repo>) {
        results.items.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ResultRepoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repo) {
            binding.repo = repo
            binding.executePendingBindings()
        }

        fun unbind() {
            binding.unbind()
        }
    }

    internal fun isItemCountMet(): Boolean {
        return getPotentialItemCount() > itemCount
    }
}

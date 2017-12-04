package com.victorude.github.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.victorude.github.R
import com.victorude.github.databinding.ResultRepoItemBinding
import com.victorude.github.model.Repo
import com.victorude.github.model.Result

class SearchResultAdapter(
        private val results: Result<List<Repo>>,
        private val onClickListener: SearchItemClickListener)
    : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo: Repo = results.items[position]
        holder.bind(repo)
        loadImage(repo.owner.avatar_url, holder.itemView)
        holder.itemView.setOnClickListener {
            val args = repo.full_name.split('/')
            onClickListener.onItemClick(args[0], args[1])
        }
    }

    private fun loadImage(avatar_url: String, view: View) {
        val imageView: ImageView = view.findViewById(R.id.icon)
        Picasso.with(view.context).load(avatar_url).into(imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ResultRepoItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return results.items.size
    }

    class ViewHolder(private val binding: ResultRepoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repo) {
            binding.repo = repo
            binding.executePendingBindings()
        }
    }
}

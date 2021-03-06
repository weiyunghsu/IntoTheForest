package com.weiyung.intotheforest.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.databinding.ItemFavoriteBinding

class FavoriteAdapter(val viewModel: FavoriteViewModel) :
    ListAdapter<Article, FavoriteAdapter.FavoriteViewHolder>(DiffCallback) {

    class FavoriteViewHolder(private var binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article, viewModel: FavoriteViewModel) {
            binding.article = article
            binding.viewModel = viewModel
            binding.toDetailButton.setOnClickListener { viewModel.toDetail(article) }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            ItemFavoriteBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel)
    }
}

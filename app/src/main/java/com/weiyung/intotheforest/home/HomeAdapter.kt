package com.weiyung.intotheforest.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.databinding.ItemHomeBinding

class HomeAdapter (private val onClickListener: OnClickListener):
    ListAdapter<Article, HomeAdapter.ViewHolder>(DiffCallback){
        class ViewHolder(private var binding: ItemHomeBinding):
                RecyclerView.ViewHolder(binding.root){
                    fun bind(article: Article){
                        binding.article = article
                        binding.executePendingBindings()
                    }
                }
    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(article)
        }
        holder.bind(article)
    }
    class OnClickListener(val clickListener: (article: Article) -> Unit) {
        fun onClick(article: Article) = clickListener(article)
    }
}
package com.weiyung.intotheforest.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.databinding.ItemHomeBinding

class HomeAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Article, HomeAdapter.ViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (article: Article) -> Unit) {
        fun onClick(article: Article) = clickListener(article)
    }
    class ViewHolder(private var binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article,onClickListener: OnClickListener) {
            binding.article = article
            binding.root.setOnClickListener { onClickListener.onClick(article) }
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

        private const val ITEM_VIEW_TYPE_ARTICLE = 0x00
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ARTICLE -> ViewHolder(
                ItemHomeBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
//            ViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(article)
        }
        holder.bind(article,onClickListener)
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_TYPE_ARTICLE
    }


}
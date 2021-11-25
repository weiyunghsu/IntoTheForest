package com.weiyung.intotheforest.detail

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weiyung.intotheforest.database.Image
import com.weiyung.intotheforest.databinding.ItemDetailGalleryBinding

class DetailAdapter : ListAdapter<String, DetailAdapter.ImageViewHolder>
(DetailAdapter) {
    private lateinit var context: Context
    private var images: Image? = null

    class ImageViewHolder(private val binding: ItemDetailGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(images: String) {
            binding.imageUrl = images
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
        ImageViewHolder {
        return ImageViewHolder(
            ItemDetailGalleryBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = getItem(position)
        holder.bind(imageUrl)
    }

    companion object DetailDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount():${super.getItemCount()}")
        return super.getItemCount()
    }
}

package com.weiyung.intotheforest

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun imageUrl(imgView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imageUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
            )
            .into(imgView)
    }
}

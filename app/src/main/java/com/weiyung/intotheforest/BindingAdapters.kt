package com.weiyung.intotheforest

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.ext.toDisplayFormat
import com.weiyung.intotheforest.home.HomeAdapter
import com.weiyung.intotheforest.network.LoadApiStatus

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

@BindingAdapter("articles")
fun bindRecyclerView(recyclerView: RecyclerView, homeItems: List<Article>?) {
    homeItems?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is HomeAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("timeToDisplayFormat")
fun bindDisplayFormatTime(textView: TextView, time: Long?) {
    textView.text = time?.toDisplayFormat()
}

@BindingAdapter("title")
fun EditText.bindAnyToString(value: Any?) {
    value?.let {
        setText(value.toString())
    }
}

@BindingAdapter("setupApiStatus")
fun bindApiStatus(view: ProgressBar, status: LoadApiStatus?) {
    when (status) {
        LoadApiStatus.LOADING -> view.visibility = View.VISIBLE
        LoadApiStatus.DONE, LoadApiStatus.ERROR -> view.visibility = View.GONE
    }
}

@BindingAdapter("setupApiErrorMessage")
fun bindApiErrorMessage(view: TextView, message: String?) {
    when (message) {
        null, "" -> {
            view.visibility = View.GONE
        }
        else -> {
            view.text = message
            view.visibility = View.VISIBLE
        }
    }
}




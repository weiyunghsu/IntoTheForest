package com.weiyung.intotheforest.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weiyung.intotheforest.database.Article

class DetailViewModelFactory (private val article: Article) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(article) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
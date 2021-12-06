package com.weiyung.intotheforest.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.database.source.IntoTheForestRepository
import com.weiyung.intotheforest.detail.DetailViewModel

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(
    private val repository: IntoTheForestRepository,
    private val article: Article
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository, article) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

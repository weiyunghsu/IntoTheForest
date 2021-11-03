package com.weiyung.intotheforest.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.database.source.IntoTheForestRepository

class DetailViewModel(
    private val repository: IntoTheForestRepository,
    private val article: Article
) : ViewModel(){
    private val _selectedArticle = MutableLiveData<Article>()
    val selectedArticle: LiveData<Article>
        get() = _selectedArticle
    init {
        _selectedArticle.value = article
    }
}
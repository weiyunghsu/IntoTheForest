package com.weiyung.intotheforest.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weiyung.intotheforest.database.Article

class HomeViewModel : ViewModel(){
    private val _naviToSelectedArticle = MutableLiveData<Article>()
    val naviToSelectedArticle : LiveData<Article>
        get() = _naviToSelectedArticle

    fun displayDetail(article: Article){
        _naviToSelectedArticle.value = article
    }
    fun displayDetailAll(){
        _naviToSelectedArticle.value = null
    }
}
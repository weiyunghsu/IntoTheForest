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
    var list = mutableListOf(
        Article(1,"2021.01","2021.10","aaaaa",1,"AA",
            "https://api.appworks-school.tw/assets/201807202157/main.jpg"),
        Article(2,"2021.01","2021.10","aaaaa",2,"BB",
            "https://api.appworks-school.tw/assets/201807202157/main.jpg"),
        Article(3,"2021.01","2021.10","aaaaa",3,"CC",
            "https://api.appworks-school.tw/assets/201807202157/main.jpg"),
        Article(4,"2021.01","2021.10","aaaaa",4,"DD",
            "https://api.appworks-school.tw/assets/201807202157/main.jpg"),
        Article(5,"2021.01","2021.10","aaaaa",5,"EE",
            "https://api.appworks-school.tw/assets/201807202157/main.jpg"),
        Article(6,"2021.01","2021.10","aaaaa",6,"FF",
            "https://api.appworks-school.tw/assets/201807202157/main.jpg"),
        Article(7,"2021.01","2021.10","aaaaa",1,"GG",
            "https://api.appworks-school.tw/assets/201807202157/main.jpg"),
        Article(8,"2021.01","2021.10","aaaaa",1,"HH",
            "https://api.appworks-school.tw/assets/201807202157/main.jpg"),
        Article(9,"2021.01","2021.10","aaaaa",1,"II",
            "https://api.appworks-school.tw/assets/201807202157/main.jpg"),
        Article(10,"2021.01","2021.10","aaaaa",1,"JJ",
            "https://api.appworks-school.tw/assets/201807202157/main.jpg"),)

}
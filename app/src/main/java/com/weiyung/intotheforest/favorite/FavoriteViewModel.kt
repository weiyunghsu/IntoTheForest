package com.weiyung.intotheforest.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.database.Favorite
import com.weiyung.intotheforest.database.User
import com.weiyung.intotheforest.database.source.IntoTheForestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class FavoriteViewModel(repository: IntoTheForestRepository) : ViewModel(){

    private val _navigateToDetail = MutableLiveData<Article>()
    val navigateToDetail: LiveData<Article>
        get() = _navigateToDetail

    fun toDetail(article: Article) {
        _navigateToDetail.value = article
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

//    val favorite: LiveData<List<Favorite>> = repository.getFavorite()

    private val _articleList = MutableLiveData<List<Article>>()
    val articleList: LiveData<List<Article>>
        get() = _articleList

//    fun transform() {
//        val articleList = mutableListOf<Article>()
//        favorite.value?.forEach {
//            articleList.add(
//                Article(
//                    it.id,
//                    it.startDate,
//                    it.endDate,
//                    it.user,
//                    it.title,
//                    it.story,
//                    it.mainImage,
//                    it.images,
//                )
//            )
//            Log.i("i-after","$articleList")
//        }
//        _articleList.value = articleList
//    }
}
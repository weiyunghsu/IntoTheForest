package com.weiyung.intotheforest.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.database.source.IntoTheForestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

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
    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

//    val favorite: LiveData<List<Favorite>> = repository.getFavorites()

    private val _isInserted = MutableLiveData<Boolean>()
    val isInserted: LiveData<Boolean>
        get() = _isInserted

    fun hasFavoriteData() {
        _isInserted.value = true
    }

    fun noFavoriteData() {
        _isInserted.value = false
    }

//    fun insertToFavorite() {
//        favorite.value?.let {
//            coroutineScope.launch {
//                val favorite = Favorite(
//                    id = it.id,
//                    startDate = it.startDate,
//                    endDate = it.endDate,
//                    user = it.user,
//                    title = it.title,
//                    mainImage = it.mainImage,
//                    articleID = article.id,
//                    whoLikeThisID = UserManager.addUserInfo().id
//                )
//                if (isInserted.value == true) {
//                    repository.removeArticleInFavorite(it.id)
//                } else {
//                    repository.insertArticleInFavorite(favorite)
//                }
//            }
//        }
//    }
}
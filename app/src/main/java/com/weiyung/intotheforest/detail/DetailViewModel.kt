package com.weiyung.intotheforest.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.database.source.IntoTheForestRepository
import com.weiyung.intotheforest.network.LoadApiStatus
import com.weiyung.intotheforest.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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
    private val _favoriteAdded = MutableLiveData<Boolean>().apply{
        value = article.followers?.contains(requireNotNull(UserManager.addUserInfo().id))
    }
    val favoriteAdded: LiveData<Boolean>
        get() = _favoriteAdded

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _favStatus = MutableLiveData<LoadApiStatus>()
    val favStatus: LiveData<LoadApiStatus>
        get() = _favStatus

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

//    val favorite: LiveData<List<Favorite>> = repository.getFavorites()

//    private val _isInserted = MutableLiveData<Boolean>()
//    val isInserted: LiveData<Boolean>
//        get() = _isInserted

//    fun hasFavoriteData() {
//        _isInserted.value = true
//    }
//
//    fun noFavoriteData() {
//        _isInserted.value = false
//    }

    fun switchState(){
        when(favoriteAdded.value){
            true -> removeUserFromFollowers(requireNotNull(UserManager.addUserInfo().id), article)
            else -> addUserToFollowers(requireNotNull(UserManager.addUserInfo().id), article)
        }
    }

    private fun addUserToFollowers(userId: String, article: Article){
        coroutineScope.launch {
            _favStatus.value = LoadApiStatus.LOADING
            val result = repository.addUserToFollowers(userId,article)
            _favoriteAdded.value = result.handleResultWith(_error, _favStatus)
        }
    }
    private fun removeUserFromFollowers(userId: String, article: Article){
        coroutineScope.launch {
            _favStatus.value = LoadApiStatus.LOADING
            val result = repository.removeUserFromFollowers(userId, article)
            _favoriteAdded.value = result.handleResultWith(_error, _favStatus)
        }
    }
}
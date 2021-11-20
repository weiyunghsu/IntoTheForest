package com.weiyung.intotheforest.favorite

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weiyung.intotheforest.IntoTheForestApplication
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.database.Result
import com.weiyung.intotheforest.database.source.IntoTheForestRepository
import com.weiyung.intotheforest.network.LoadApiStatus
import com.weiyung.intotheforest.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoriteViewModel(val repository: IntoTheForestRepository) : ViewModel(){

//    private val _favorite = MutableLiveData<Favorite>()
//    val favorite: LiveData<Favorite>
//        get() = _favorite
    private var _articleAllList = MutableLiveData<List<Article>>()
    val articleAllList: LiveData<List<Article>>
        get() = _articleAllList

    private var _articleList = MutableLiveData<List<Article>>()
    val articleList: LiveData<List<Article>>
        get() = _articleList

    var liveFavorites = MutableLiveData<List<Article>>()

    private val _navigateToDetail = MutableLiveData<Article>()
    val navigateToDetail: LiveData<Article>
        get() = _navigateToDetail

    fun toDetail(article: Article) {
        _navigateToDetail.value = article
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    init {
        Log.i("TEST", "UserManager.addUserInfo().id:${UserManager.addUserInfo().id}")
        getFavorites(requireNotNull(UserManager.addUserInfo().id))
    }
    fun select(article: Article) {
        _navigateToDetail.value = article
    }

//    val favorite: MutableLiveData<List<Article>> = repository.getFavorites()

//    private val _favoriteList = MutableLiveData<List<Article>>()
//    val favoriteList: LiveData<List<Article>>
//        get() = _favoriteList

//    fun transform() {
//        val followerList = mutableListOf<Article>()
//        followerList.add(
//                Favorite(
//                    followers =
//                )
//            )
//            Log.i("i-after","$favoriteList")
//        }
//        _favoriteList.value = favoriteList
//    fun removeFavorite(favorite: Favorite) {
//        coroutineScope.launch {
//            repository?.removeArticleInFavorite(favorite.id.toLong())
//        }
//    }

    private fun getFavorites(userId: String) {
//        coroutineScope.launch {
//            _articleAllList = repository.getFavorites(userId)
            _articleList = repository.getFavorites(userId)
            _status.value = LoadApiStatus.DONE
            Log.i(TAG,"articleAllList: ${articleAllList.value}")
            Log.i(TAG,"articleList: ${articleList.value}")
//            _status.value = LoadApiStatus.LOADING
//            repository.getFavorites(userId).apply{
//                _articleAllList.value = handleResultWith(_error, _status)
//                _articleList.value = articleAllList.value
//            }
//        }
    }
}




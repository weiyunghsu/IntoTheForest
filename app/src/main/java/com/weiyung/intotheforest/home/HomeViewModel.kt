package com.weiyung.intotheforest.home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weiyung.intotheforest.IntoTheForestApplication
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.database.User
import com.weiyung.intotheforest.database.Result
import com.weiyung.intotheforest.database.source.IntoTheForestRepository
import com.weiyung.intotheforest.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: IntoTheForestRepository): ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    var _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>>
        get() = _articles

    var liveArticles = MutableLiveData<List<Article>>()

    private val _naviToSelectedArticle = MutableLiveData<Article>()
    val naviToSelectedArticle: LiveData<Article>
        get() = _naviToSelectedArticle

    fun displayDetail(article: Article) {
        _naviToSelectedArticle.value = article
    }

    fun displayDetailAll() {
        _naviToSelectedArticle.value = null
    }

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _refreshStatus = MutableLiveData<Boolean>()
    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        Log.i(TAG, "------------------------------------")
        Log.i(TAG, "[${this::class.simpleName}]${this}")
        Log.i(TAG, "------------------------------------")

        if (IntoTheForestApplication.instance.isLiveDataDesign()) {
            getLiveArticlesResult()
        } else {
            getArticlesResult()
        }
    }

    fun getArticlesResult() {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            val result = repository.getArticles()
            _articles.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value =
                        IntoTheForestApplication.instance.getString(R.string.nothingHappen)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }

    fun getLiveArticlesResult() {
        liveArticles = repository.getLiveArticles()
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false
    }
}
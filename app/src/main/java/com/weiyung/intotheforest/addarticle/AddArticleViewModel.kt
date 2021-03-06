package com.weiyung.intotheforest.addarticle

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

class AddArticleViewModel(
    private val repository: IntoTheForestRepository
) : ViewModel() {

    val _article = MutableLiveData<Article>().apply {
        value = Article(
            user = UserManager.addUserInfo(),
            followers = listOf()
        )
    }
    val article: LiveData<Article>
        get() = _article

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    var isUploadSuccess: Boolean = false

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    init {
        Log.i(TAG, "------------------------------------")
        Log.i(TAG, "[${this::class.simpleName}]$this")
        Log.i(TAG, "------------------------------------")
    }

    fun addData(article: Article) {
        coroutineScope.launch {

            Log.i(TAG, "ViewModel fun addData : $article")
            _status.value = LoadApiStatus.LOADING

            when (val result = repository.publish(article)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = IntoTheForestApplication
                        .instance.getString(R.string.nothingHappen)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }
}

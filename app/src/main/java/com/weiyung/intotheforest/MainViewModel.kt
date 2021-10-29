package com.weiyung.intotheforest

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weiyung.intotheforest.database.User
import com.weiyung.intotheforest.database.source.IntoTheForestRepository
import com.weiyung.intotheforest.util.CurrentFragmentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MainViewModel(private val repository: IntoTheForestRepository) : ViewModel() {
    val currentFragmentType = MutableLiveData<CurrentFragmentType>()

    private val _user = MutableLiveData<User>().apply {
        value = User(
            "9527",
            "九五二七",
            "aaa@gmail.com"
        )
    }
    val user : LiveData<User>
        get() = _user

    private val _refresh = MutableLiveData<Boolean>()

    val refresh: LiveData<Boolean>
        get() = _refresh

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    init {
        Log.i(TAG,"------------------------------------")
        Log.i(TAG,"[${this::class.simpleName}]${this}")
        Log.i(TAG,"------------------------------------")
    }

    fun refresh() {
        if (!IntoTheForestApplication.instance.isLiveDataDesign()) {
            _refresh.value = true
        }
    }

    fun onRefreshed() {
        if (!IntoTheForestApplication.instance.isLiveDataDesign()) {
            _refresh.value = null
        }
    }

}
package com.weiyung.intotheforest.map

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.weiyung.intotheforest.IntoTheForestApplication
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.database.Result
import com.weiyung.intotheforest.database.Route
import com.weiyung.intotheforest.database.source.IntoTheForestRepository
import com.weiyung.intotheforest.network.LoadApiStatus
import kotlinx.coroutines.*

class MapViewModel(private val repository: IntoTheForestRepository,
                   ): ViewModel(){

    var _routes = MutableLiveData<List<Route>>()
    val routes: LiveData<List<Route>>
        get() = _routes

    var liveRoutes = MutableLiveData<List<Route>>()

    var _tracks = MutableLiveData<List<LatLng>>()
    val tracks: LiveData<List<LatLng>>
        get() = _tracks

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
            getLiveRoutesResult()
        } else {
            getRoutesResult()
        }
    }
    fun getRoutesResult() {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            val result = repository.getRoutes()
            _routes.value = when (result) {
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
                        IntoTheForestApplication.instance.getString(R.string.nothing_happen)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }

    fun getLiveRoutesResult() {
        liveRoutes = repository.getLiveRoutes()
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false
    }
}
package com.weiyung.intotheforest.map

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.weiyung.intotheforest.IntoTheForestApplication
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.database.Result
import com.weiyung.intotheforest.database.Route
import com.weiyung.intotheforest.database.source.IntoTheForestRepository
import com.weiyung.intotheforest.network.LoadApiStatus
import kotlinx.coroutines.*

class MapViewModel(
    private val repository: IntoTheForestRepository,
//    private val route: Route
    ): ViewModel(){

    var _routes = MutableLiveData<List<Route>>()
    val routes: LiveData<List<Route>>
        get() = _routes

//    init {
//        _routes.value = listOf(route)
//    }

    var liveRoutes = MutableLiveData<List<Route>>()

    var _tracks = MutableLiveData<List<LatLng>>()
    val tracks: LiveData<List<LatLng>>
        get() = _tracks

    val selectedRoutePosition = MutableLiveData<Int>()

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _refreshStatus = MutableLiveData<Boolean>()
    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    var isMapReady : Boolean = false

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

    val appWorksSchoolPeak = LatLng(25.042477, 121.564879)
    val source = LatLng(25.027389, 121.570825) //starting point (LatLng)
    val destination = LatLng(25.036462, 121.587468) // ending point (LatLng)

    val source1 = LatLng(25.037770, 121.585125) //starting point (LatLng)
    val destination1 = LatLng(25.027426, 121.570723) // ending point (LatLng)

    val wc1 = arrayListOf(LatLng(25.036452, 121.584763),
        LatLng(25.032223, 121.591132),
        LatLng(25.027529, 121.579818),
        LatLng(25.026820, 121.575029),)

    val view1 = arrayListOf(LatLng(25.031359, 121.583593),
        LatLng(25.029347, 121.582627),
        LatLng(25.026738, 121.580566),
        LatLng(25.026827, 121.574412),)
    val eat1 = arrayListOf(LatLng(25.026853, 121.568260))

    val source2 = LatLng(25.128404, 121.4243393) //starting point (LatLng)
    val destination2 = LatLng(25.136002, 121.426657) // ending point (LatLng)
    val view2 = arrayListOf(LatLng(25.131885, 121.423775))
    val eat2 = arrayListOf(LatLng(25.131865, 121.420749))

    val routeLine1 = arrayListOf(
        LatLng(25.03777004, 121.5851248),
        LatLng(25.03760966, 121.5851441),
        LatLng(25.03766924, 121.5847797),
        LatLng(25.03748911, 121.5845986),
        LatLng(25.03762518, 121.5845815),
        LatLng(25.03761801, 121.5844368),
        LatLng(25.03749285, 121.5842936),
        LatLng(25.03753846, 121.584049),
        LatLng(25.03763843, 121.5840077),
        LatLng(25.03761785, 121.5838835),
        LatLng(25.03757775, 121.5840136),
        LatLng(25.03767322, 121.5838994),
        LatLng(25.03771398, 121.5840411),
        LatLng(25.03756067, 121.5842181),
        LatLng(25.03764354, 121.5845786),
        LatLng(25.0373701, 121.5844366),
        LatLng(25.03665585, 121.5844648),
        LatLng(25.03660004, 121.5844448),
        LatLng(25.03654226, 121.5845503),
        LatLng(25.03604879, 121.5844534),
        LatLng(25.0359807, 121.584414),
        LatLng(25.03565817, 121.5842585),
        LatLng(25.03562465, 121.5840923),
        LatLng(25.03496406, 121.5839086),
        LatLng(25.03483424, 121.5835593),
        LatLng(25.03478131, 121.5835552),
        LatLng(25.03484956, 121.5835606),
        LatLng(25.03478414, 121.5835144),
        LatLng(25.03481108, 121.5835686),
        LatLng(25.03483978, 121.5835227),
        LatLng(25.03486311, 121.5835507),
        LatLng(25.03426417, 121.5834449),
        LatLng(25.0336333, 121.5832909),
        LatLng(25.0335369, 121.5830175),
        LatLng(25.0332081, 121.5827078),
        LatLng(25.03280152, 121.5829787),
        LatLng(25.03242756, 121.583051),
        LatLng(25.03210405, 121.5833928),
        LatLng(25.03180328, 121.583447),
        LatLng(25.03154042, 121.5833759),
        LatLng(25.03153579, 121.5835304),
        LatLng(25.03139717, 121.5836216),
        LatLng(25.0313306, 121.583616),
        LatLng(25.03143523, 121.5835936),
        LatLng(25.03048732, 121.5839604),
        LatLng(25.02990869, 121.5838352),
        LatLng(25.02929144, 121.5836871),
        LatLng(25.02906092, 121.5832685),
        LatLng(25.02937219, 121.5829051),
        LatLng(25.02939755, 121.5826427),
        LatLng(25.02920177, 121.5831004),
        LatLng(25.02811899, 121.5836414),
        LatLng(25.02787438, 121.5835338),
        LatLng(25.02769285, 121.5827652),
        LatLng(25.02749035, 121.5827802),
        LatLng(25.02725884, 121.582821),
        LatLng(25.02704914, 121.5828245),
        LatLng(25.02697559, 121.5826212),
        LatLng(25.02699117, 121.5823525),
        LatLng(25.02681248, 121.58234),
        LatLng(25.02633524, 121.5824533),
        LatLng(25.02585948, 121.5822764),
        LatLng(25.02577623, 121.5820508),
        LatLng(25.02567021, 121.5818054),
        LatLng(25.02580594, 121.5807026),
        LatLng(25.02596778, 121.5801566),
        LatLng(25.02633389, 121.5796685),
        LatLng(25.02643028, 121.5793706),
        LatLng(25.02661636, 121.579085),
        LatLng(25.02628158, 121.5786939),
        LatLng(25.02596314, 121.5785429),
        LatLng(25.02594517, 121.5782315),
        LatLng(25.02570025, 121.5779918),
        LatLng(25.02576026, 121.5778097),
        LatLng(25.02573579, 121.5776373),
        LatLng(25.0258557, 121.5771784),
        LatLng(25.02608322, 121.5765739),
        LatLng(25.02653881, 121.5759908),
        LatLng(25.02668535, 121.5758654),
        LatLng(25.02713209, 121.5761999),
        LatLng(25.02734902, 121.576224),
        LatLng(25.02721779, 121.5738055),
        LatLng(25.02731951, 121.5738214),
        LatLng(25.02730648, 121.5737951),
        LatLng(25.02777353, 121.5734782),
        LatLng(25.0277029, 121.5734348),
        LatLng(25.02799688, 121.5733655),
        LatLng(25.02803582, 121.5733879),
        LatLng(25.02801546, 121.5733887),
        LatLng(25.02802147, 121.5733837),
        LatLng(25.02746765, 121.5733578),
        LatLng(25.02705016, 121.5729733),
        LatLng(25.02706854, 121.5726478),
        LatLng(25.0271783, 121.5723045),
        LatLng(25.0272838, 121.572011),
        LatLng(25.0276358, 121.5712428),
        LatLng(25.02742634, 121.5707231),)

    val routeLine2 = arrayListOf(
        LatLng(25.12840413, 121.4243393),
            LatLng(25.12865257, 121.4245413),
            LatLng(25.12860906, 121.4247674),
            LatLng(25.1287974, 121.4247676),
            LatLng(25.12883275, 121.4250032),
            LatLng(25.13016919, 121.4243243),
            LatLng(25.13032353, 121.4240143),
            LatLng(25.13026883, 121.4238402),
            LatLng(25.13052125, 121.4238118),
            LatLng(25.13065222, 121.4238955),
            LatLng(25.13074858, 121.4237415),
            LatLng(25.13120103, 121.4238109),
            LatLng(25.13121045, 121.4237361),
            LatLng(25.13114471, 121.4237062),
            LatLng(25.13123592, 121.4234883),
            LatLng(25.13187724, 121.4237279),
            LatLng(25.13191249, 121.4236797),
            LatLng(25.13196211, 121.4242289),
            LatLng(25.13220745, 121.4242195),
            LatLng(25.13241931, 121.4242586),
            LatLng(25.13259649, 121.4244102),
            LatLng(25.13280678, 121.4244331),
            LatLng(25.13340377, 121.4239637),
            LatLng(25.13410705, 121.4236791),
            LatLng(25.13448562, 121.4237402),
            LatLng(25.13533946, 121.4236584),
            LatLng(25.13564681, 121.4241001),
            LatLng(25.13607155, 121.425274),
            LatLng(25.13606423, 121.4260138),
            LatLng(25.13600372, 121.4266437),
            LatLng(25.13600172, 121.4266574),
            LatLng(25.13600172, 121.4266574),)
}
package com.weiyung.intotheforest.database

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Route(
    var routeId: Int = 0,
    var seg: Int = 0,
    var ele: Double? = null,
    var geopoint: LatLng? = null,
    var time: Timestamp? = null,
) : Parcelable

@Parcelize
data class Track(
    var routeId: Int = 0,
    var seg: Int = 0,
    var points: List<LatLng>? = null
) : Parcelable

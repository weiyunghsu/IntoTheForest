package com.weiyung.intotheforest.database

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    var picture: String = ""
) : Parcelable

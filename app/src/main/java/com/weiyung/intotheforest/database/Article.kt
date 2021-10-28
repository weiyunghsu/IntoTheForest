package com.weiyung.intotheforest.database

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article (
    var id: String,
    var createdTime: Long = -1,
    val startDate : String,
    val endDate : String,
    val user : User? = null,
    val title : String,
    val story : String,
    val mainImage: String,
    val images : List<String>,
    ): Parcelable

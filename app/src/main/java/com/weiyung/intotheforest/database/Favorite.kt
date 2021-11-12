package com.weiyung.intotheforest.database

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorite(
    var id: String = "",
//    var createdTime: Long = -1,
    var startDate: String = "",
    var endDate: String = "",
    var user: User? = null,
    var title: String = "",
    var story: String = "",
    var mainImage: String = "",
    var images: List<String>? = null
): Parcelable
package com.weiyung.intotheforest.database

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Article(
    var id: String = "",
//    var createdTime: Long = -1,
    var startDate: String = "",
    var endDate: String = "",
    var user: User? = null,
    var title: String = "",
    var story: String = "",
    var mainImage: String = "",
    var images: List<String>? = null,
    var followers: List<String>? = null,
): Parcelable

@Parcelize
data class Image(
    var image1 : String = "",
    var image2 : String = "",
    var image3 : String = "",
    var image4 : String = "",
    var image5 : String = "",
): Parcelable

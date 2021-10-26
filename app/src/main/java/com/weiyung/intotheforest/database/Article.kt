package com.weiyung.intotheforest.database

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article (
    val articleId : Int,
    val startDate : String,
    val endDate : String,
    val title : String,
    val authorId : Int,
    val author : String,
    val image : String,
    val story : String
    ): Parcelable

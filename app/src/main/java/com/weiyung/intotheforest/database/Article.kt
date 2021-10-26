package com.weiyung.intotheforest.database

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article (
    val article_id : Int,
    val start_date : String,
    val end_date : String,
    val title : String,
    val author_id : Int,
    val author : String,
    val image : String,
    val story : String
    ): Parcelable

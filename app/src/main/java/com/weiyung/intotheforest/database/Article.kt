package com.weiyung.intotheforest.database

import android.widget.DatePicker

data class Article (
    val article_id : Int,
    val start_date : String,
    val end_date : String,
    val title : String,
    val author_id : Int,
    val author : String,
    val image : String
    )

package com.weiyung.intotheforest.database

import android.widget.DatePicker

data class Article (
    val article_id : Int,
    val start_date : DatePicker,
    val end_date : DatePicker,
    val title : String,
    val author_id : Int
    )

package com.weiyung.intotheforest.util

import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.util.Util.getString

enum class CurrentFragmentType(val value: String) {
    HOME(getString(R.string.home)),
    FAVORITE(getString(R.string.favorite)),
    ADDARTICLE(getString(R.string.addArticle)),
    MAP(getString(R.string.map)),
    USER(getString(R.string.user)),
    DETAIL(getString(R.string.detail)),
    LOGIN(""),
    WEATHER(getString(R.string.weather)),
    REPORT(getString(R.string.report))
}

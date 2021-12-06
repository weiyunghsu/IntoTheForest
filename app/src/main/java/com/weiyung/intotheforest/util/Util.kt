package com.weiyung.intotheforest.util

import com.weiyung.intotheforest.IntoTheForestApplication

object Util {
    fun getString(resourceId: Int): String {
        return IntoTheForestApplication.instance.getString(resourceId)
    }
}

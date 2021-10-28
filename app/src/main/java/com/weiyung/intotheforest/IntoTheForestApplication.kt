package com.weiyung.intotheforest

import android.app.Application
import com.weiyung.intotheforest.database.source.IntoTheForestRepository
import com.weiyung.intotheforest.util.ServiceLocator
import kotlin.properties.Delegates

class IntoTheForestApplication : Application(){

    val intoTheForestRepository: IntoTheForestRepository
        get() = ServiceLocator.provideRepository(this)

    companion object {
        var instance: IntoTheForestApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    fun isLiveDataDesign() = true
}
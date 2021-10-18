package com.weiyung.intotheforest

import android.app.Application
import kotlin.properties.Delegates

class IntoTheForestApplication : Application(){

//    val intoTheForestRepository: IntoTheForestRepository
//        get() = ServiceLocator.provideTasksRepository(this)

    companion object {
        var instance: IntoTheForestApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
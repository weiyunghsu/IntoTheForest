package com.weiyung.intotheforest.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.weiyung.intotheforest.database.source.DefaultIntoTheForestRepository
import com.weiyung.intotheforest.database.source.IntoTheForestDataSource
import com.weiyung.intotheforest.database.source.IntoTheForestRepository
import com.weiyung.intotheforest.database.source.local.IntoTheForestLocalDataSource
import com.weiyung.intotheforest.database.source.remote.IntoTheForestRemoteDataSource

object ServiceLocator {
    @Volatile
    var repository: IntoTheForestRepository? = null
        @VisibleForTesting set
    fun provideRepository(context: Context): IntoTheForestRepository {
        synchronized(this) {
            return repository
                ?: repository
                ?: createIntoTheForestRepository(context)
        }
    }
    private fun createIntoTheForestRepository(context: Context): IntoTheForestRepository {
        return DefaultIntoTheForestRepository(
            IntoTheForestRemoteDataSource,
            createLocalDataSource(context)
        )
    }
    private fun createLocalDataSource(context: Context): IntoTheForestDataSource {
        return IntoTheForestLocalDataSource(context)
    }
}
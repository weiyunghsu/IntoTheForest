package com.weiyung.intotheforest.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weiyung.intotheforest.database.Route
import com.weiyung.intotheforest.database.source.IntoTheForestRepository
import com.weiyung.intotheforest.map.MapViewModel

@Suppress("UNCHECKED_CAST")
class MapViewModelFactory(
    private val repository: IntoTheForestRepository,
    private val route: Route
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

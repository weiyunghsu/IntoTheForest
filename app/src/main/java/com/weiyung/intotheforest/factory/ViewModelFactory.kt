package com.weiyung.intotheforest.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weiyung.intotheforest.MainViewModel
import com.weiyung.intotheforest.database.source.IntoTheForestRepository
import com.weiyung.intotheforest.home.HomeViewModel
import com.weiyung.intotheforest.login.LoginViewModel
import com.weiyung.intotheforest.map.MapViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val repository: IntoTheForestRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(repository)

                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(repository)

                isAssignableFrom(MapViewModel::class.java) ->
                    MapViewModel(repository)

                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
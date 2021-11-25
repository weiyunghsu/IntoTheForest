package com.weiyung.intotheforest.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weiyung.intotheforest.MainViewModel
import com.weiyung.intotheforest.addarticle.AddArticleViewModel
import com.weiyung.intotheforest.database.source.IntoTheForestRepository
import com.weiyung.intotheforest.favorite.FavoriteViewModel
import com.weiyung.intotheforest.home.HomeViewModel
import com.weiyung.intotheforest.login.LoginViewModel
import com.weiyung.intotheforest.map.MapViewModel
import com.weiyung.intotheforest.reportdialog.ReportViewModel
import com.weiyung.intotheforest.user.UserViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val repository: IntoTheForestRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel()

                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(repository)

                isAssignableFrom(MapViewModel::class.java) ->
                    MapViewModel(repository)

                isAssignableFrom(FavoriteViewModel::class.java) ->
                    FavoriteViewModel(repository)

                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(repository)

                isAssignableFrom(UserViewModel::class.java) ->
                    UserViewModel(repository)

                isAssignableFrom(ReportViewModel::class.java) ->
                    ReportViewModel()

                isAssignableFrom(AddArticleViewModel::class.java) ->
                    AddArticleViewModel(repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}

package com.weiyung.intotheforest.ext

import androidx.fragment.app.Fragment
import com.weiyung.intotheforest.IntoTheForestApplication
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.database.Route
import com.weiyung.intotheforest.factory.DetailViewModelFactory
import com.weiyung.intotheforest.factory.MapViewModelFactory
import com.weiyung.intotheforest.factory.ViewModelFactory

fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as IntoTheForestApplication).repository
    return ViewModelFactory(repository)
}

fun Fragment.getVmFactory(article: Article): DetailViewModelFactory {
    val repository = (requireContext().applicationContext as IntoTheForestApplication).repository
    return DetailViewModelFactory(repository, article)
}

fun Fragment.getVmFactory(route: Route): MapViewModelFactory {
    val repository = (requireContext().applicationContext as IntoTheForestApplication).repository
    return MapViewModelFactory(repository, route)
}
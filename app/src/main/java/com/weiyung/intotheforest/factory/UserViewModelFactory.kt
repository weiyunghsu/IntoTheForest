package com.weiyung.intotheforest.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weiyung.intotheforest.addarticle.AddArticleViewModel
import com.weiyung.intotheforest.database.User
import com.weiyung.intotheforest.database.source.IntoTheForestRepository

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(
    private val repository: IntoTheForestRepository,
    private val user: User?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AddArticleViewModel::class.java)) {
            return AddArticleViewModel(repository, user) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
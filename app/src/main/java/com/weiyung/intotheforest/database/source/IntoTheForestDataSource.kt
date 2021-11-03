package com.weiyung.intotheforest.database.source

import androidx.lifecycle.MutableLiveData
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.database.Result
import com.weiyung.intotheforest.database.Route
import com.weiyung.intotheforest.database.User

interface IntoTheForestDataSource {
    suspend fun login(id: String): Result<User>

    suspend fun getArticles(): Result<List<Article>>

    fun getLiveArticles(): MutableLiveData<List<Article>>

    suspend fun publish(article: Article): Result<Boolean>

    suspend fun delete(article: Article): Result<Boolean>

    suspend fun getRoutes(): Result<List<Route>>

    fun getLiveRoutes(): MutableLiveData<List<Route>>
}
package com.weiyung.intotheforest.database.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.weiyung.intotheforest.database.*

interface IntoTheForestRepository {
    suspend fun loginMockData(id: String): Result<User>

    suspend fun getArticles(): Result<List<Article>>

    fun getLiveArticles(): MutableLiveData<List<Article>>

    suspend fun publish(article: Article): Result<Boolean>

    suspend fun delete(article: Article): Result<Boolean>

    suspend fun update(route: Route) : Result<Boolean>

    suspend fun getRoutes(): Result<List<Route>>

    fun getLiveRoutes(): MutableLiveData<List<Route>>

    suspend fun getFavorite(): LiveData<List<Favorite>>
}
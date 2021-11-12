package com.weiyung.intotheforest.database.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.weiyung.intotheforest.database.*

interface IntoTheForestDataSource {
    suspend fun login(id: String): Result<User>

    suspend fun getArticles(): Result<List<Article>>

    fun getLiveArticles(): MutableLiveData<List<Article>>

    suspend fun publish(article: Article): Result<Boolean>

    suspend fun delete(article: Article): Result<Boolean>

    suspend fun update(route: Route) : Result<Boolean>

    suspend fun getRoutes(): Result<List<Route>>

    fun getLiveRoutes(): MutableLiveData<List<Route>>

    suspend fun getFavorite(): LiveData<List<Favorite>>


    suspend fun getUser(userId: User?): Result<User?>

    suspend fun signUpUser(user: User): Result<Boolean>
}
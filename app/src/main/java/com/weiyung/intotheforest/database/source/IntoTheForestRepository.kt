package com.weiyung.intotheforest.database.source

import androidx.lifecycle.MutableLiveData
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.database.Result
import com.weiyung.intotheforest.database.User

interface IntoTheForestRepository {
    suspend fun loginMockData(id: String): Result<User>

    suspend fun getArticles(): Result<List<Article>>

    fun getLiveArticles(): MutableLiveData<List<Article>>

    suspend fun publish(article: Article): Result<Boolean>

    suspend fun delete(article: Article): Result<Boolean>
}
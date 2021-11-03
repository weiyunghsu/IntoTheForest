package com.weiyung.intotheforest.database.source

import androidx.lifecycle.MutableLiveData
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.database.User
import com.weiyung.intotheforest.database.Result
import com.weiyung.intotheforest.database.Route


class DefaultIntoTheForestRepository (private val remoteDataSource: IntoTheForestDataSource,
                                      private val localDataSource: IntoTheForestDataSource
) : IntoTheForestRepository{
    override suspend fun loginMockData(id: String): Result<User> {
        return localDataSource.login(id)
    }

    override suspend fun getArticles(): Result<List<Article>> {
        return remoteDataSource.getArticles()
    }

    override fun getLiveArticles(): MutableLiveData<List<Article>> {
        return remoteDataSource.getLiveArticles()
    }

    override suspend fun publish(article: Article): Result<Boolean> {
        return remoteDataSource.publish(article)
    }

    override suspend fun delete(article: Article): Result<Boolean> {
        return remoteDataSource.delete(article)
    }
    override suspend fun getRoutes(): Result<List<Route>> {
        return remoteDataSource.getRoutes()
    }
    override fun getLiveRoutes(): MutableLiveData<List<Route>> {
        return remoteDataSource.getLiveRoutes()
    }
}
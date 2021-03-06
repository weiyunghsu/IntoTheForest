package com.weiyung.intotheforest.database.source

import androidx.lifecycle.MutableLiveData
import com.weiyung.intotheforest.database.*

class DefaultIntoTheForestRepository(
    private val remoteDataSource: IntoTheForestDataSource,
    private val localDataSource: IntoTheForestDataSource
) : IntoTheForestRepository {
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
    override suspend fun update(route: Route): Result<Boolean> {
        return remoteDataSource.update(route)
    }
    override suspend fun getRoutes(): Result<List<Route>> {
        return remoteDataSource.getRoutes()
    }
    override fun getLiveRoutes(): MutableLiveData<List<Route>> {
        return remoteDataSource.getLiveRoutes()
    }
    override suspend fun publishFavorite(article: Article): Result<Boolean> {
        return remoteDataSource.publishFavorite(article)
    }

    override suspend fun addUserToFollowers(userId: String, article: Article): Result<Boolean> {
        return remoteDataSource.addUserToFollowers(userId, article)
    }

    override suspend fun removeUserFromFollowers(userId: String, article: Article): Result<Boolean> {
        return remoteDataSource.removeUserFromFollowers(userId, article)
    }

    override fun getFavorites(userId: String): MutableLiveData<List<Article>> {
        return remoteDataSource.getFavorites(userId)
    }

    override suspend fun getUser(userId: User?): Result<User?> {
        return remoteDataSource.getUser(userId)
    }

    override suspend fun signUpUser(user: User): Result<Boolean> {
        return remoteDataSource.signUpUser(user)
    }
}

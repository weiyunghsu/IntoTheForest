package com.weiyung.intotheforest.database.source.local

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.weiyung.intotheforest.database.*
import com.weiyung.intotheforest.database.source.IntoTheForestDataSource

class IntoTheForestLocalDataSource(val context: Context) : IntoTheForestDataSource {
    override suspend fun login(id: String): Result<User> {
        return when (id) {
            id -> Result.Success((User()))
            else -> Result.Fail("You have to add $id info in local data source")
        }
    }
    override suspend fun getArticles(): Result<List<Article>> {
        TODO("not implemented")
    }

    override fun getLiveArticles(): MutableLiveData<List<Article>> {
        TODO("not implemented")
    }

    override suspend fun publish(article: Article): Result<Boolean> {
        TODO("not implemented")
    }

    override suspend fun delete(article: Article): Result<Boolean> {
        TODO("not implemented")
    }

    override suspend fun update(route: Route): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getRoutes(): Result<List<Route>> {
        TODO("Not yet implemented")
    }

    override fun getLiveRoutes(): MutableLiveData<List<Route>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(userId: User?): Result<User?> {
        TODO("Not yet implemented")
    }

    override suspend fun signUpUser(user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun publishFavorite(article: Article): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getFavorites(userId: String): MutableLiveData<List<Article>> {
        TODO("Not yet implemented")
    }

    override suspend fun addUserToFollowers(userId: String, article: Article): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun removeUserFromFollowers(userId: String, article: Article): Result<Boolean> {
        TODO("Not yet implemented")
    }
}

package com.weiyung.intotheforest.database.source.local

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.database.User
import com.weiyung.intotheforest.database.Result
import com.weiyung.intotheforest.database.Route
import com.weiyung.intotheforest.database.source.IntoTheForestDataSource

class IntoTheForestLocalDataSource(val context: Context) : IntoTheForestDataSource {
    override suspend fun login(id: String): Result<User> {
        return when (id) {
            "9527" -> Result.Success((User(
                id,
                "九五二七",
                "aaa@gmail.com",
            )))
            "8787" -> Result.Success((User(
                id,
                "八七八七",
                "bbb@gmail.com"
            )))
            //TODO add your profile here
            else -> Result.Fail("You have to add $id info in local data source")
        }
    }
    override suspend fun getArticles(): Result<List<Article>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLiveArticles(): MutableLiveData<List<Article>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun publish(article: Article): Result<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(article: Article): Result<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getRoutes(): Result<List<Route>> {
        TODO("Not yet implemented")
    }

    override fun getLiveRoutes(): MutableLiveData<List<Route>> {
        TODO("Not yet implemented")
    }

}
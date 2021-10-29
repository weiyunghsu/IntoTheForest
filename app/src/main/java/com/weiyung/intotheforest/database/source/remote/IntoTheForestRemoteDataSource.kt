package com.weiyung.intotheforest.database.source.remote

import android.content.ContentValues.TAG
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.weiyung.intotheforest.IntoTheForestApplication
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.database.Result
import com.weiyung.intotheforest.database.User
import com.weiyung.intotheforest.database.source.IntoTheForestDataSource
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object IntoTheForestRemoteDataSource : IntoTheForestDataSource{
    private const val PATH_ARTICLES = "articles"
    private const val KEY_CREATED_TIME = "createdTime"
    private const val KEY_ID = "id"

    override suspend fun login(id: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getArticles(): Result<List<Article>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES)
            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Article>()
                    for (document in task.result!!) {
                        Log.d(TAG,document.id + " => " + document.data)

                        val article = document.toObject(Article::class.java)
                        list.add(article)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Log.w(TAG,"[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(
                        IntoTheForestApplication.instance.getString(
                        R.string.nothing_happen)))
                }
            }
    }

    override fun getLiveArticles(): MutableLiveData<List<Article>> {
        val liveData = MutableLiveData<List<Article>>()
        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES)
            .orderBy(KEY_ID, Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                Log.i(TAG,"addSnapshotListener detect")
                exception?.let {
                    Log.w(TAG,"[${this::class.simpleName}] Error getting documents. ${it.message}")
                }
                val list = mutableListOf<Article>()
                for (document in snapshot!!) {
                    Log.d(TAG,document.id + " => " + document.data)

                    val article = document.toObject(Article::class.java)
                    list.add(article)
                }
                liveData.value = list
            }
        return liveData
    }

    override suspend fun publish(article: Article): Result<Boolean>  = suspendCoroutine { continuation ->
        val articles = FirebaseFirestore.getInstance().collection(PATH_ARTICLES)
        val document = articles.document()

        article.id = document.id
//        article.createdTime = Calendar.getInstance().timeInMillis

        document
            .set(article)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG,"Publish: $article")

                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {

                        Log.w(TAG,"[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(IntoTheForestApplication.instance.getString(R.string.nothing_happen)))
                }
            }
    }

    override suspend fun delete(article: Article): Result<Boolean>  = suspendCoroutine { continuation ->

        when {
            article.user?.id == "9527"
//                    && article.tag.toLowerCase(Locale.TAIWAN) != "test"
//                    && article.tag.trim().isNotEmpty()
            -> {
                continuation.resume(Result.Fail("nothing happen ${article.user?.name}"))
            }
            else -> {
                FirebaseFirestore.getInstance()
                    .collection(PATH_ARTICLES)
                    .document(article.id)
                    .delete()
                    .addOnSuccessListener {
                        Log.i(TAG,"Delete: $article")

                        continuation.resume(Result.Success(true))
                    }.addOnFailureListener {
                        Log.w(TAG,"[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                    }
            }
        }
    }
}
package com.weiyung.intotheforest.database.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.weiyung.intotheforest.IntoTheForestApplication
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.database.*
import com.weiyung.intotheforest.database.source.IntoTheForestDataSource
import com.weiyung.intotheforest.util.Util
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object IntoTheForestRemoteDataSource : IntoTheForestDataSource{
    private const val PATH_ARTICLES = "articles"
    private const val KEY_CREATED_TIME = "createdTime"
    private const val KEY_ID = "id"
    private const val PATH_ROUTES = "routes"
    private const val KEY_ROUTE_ID = "routeId"
    private const val KEY_SEG = "seg"
    private const val PATH_USER = "user"
    private const val KEY_END_DATE = "endDate"

    override suspend fun login(id: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getArticles(): Result<List<Article>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES)
            .orderBy(KEY_END_DATE, Query.Direction.DESCENDING)
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
            .orderBy(KEY_END_DATE, Query.Direction.DESCENDING)
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

    override suspend fun update(route: Route): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getRoutes(): Result<List<Route>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_ROUTES)
            .orderBy(KEY_ROUTE_ID, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Route>()
                    for (document in task.result!!) {
                        Log.d(TAG,document.id + " => " + document.data)

                        val route = document.toObject(Route::class.java)
                        list.add(route)
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

    override fun getLiveRoutes(): MutableLiveData<List<Route>> {
        val liveData = MutableLiveData<List<Route>>()
        FirebaseFirestore.getInstance()
            .collection(PATH_ROUTES)
            .orderBy(KEY_ROUTE_ID, Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                Log.i(TAG,"addSnapshotListener detect")
                exception?.let {
                    Log.w(TAG,"[${this::class.simpleName}] Error getting documents. ${it.message}")
                }
                val list = mutableListOf<Route>()
                for (document in snapshot!!) {
                    Log.d(TAG,document.id + " => " + document.data)

                    val route = document.toObject(Route::class.java)
                    list.add(route)
                }
                liveData.value = list
            }
        return liveData
    }

    override suspend fun getFavorite(): LiveData<List<Favorite>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(userId: User?): Result<User?> {
        return User().getResultFrom(
            FirebaseFirestore.getInstance()
            .collection(PATH_USER).whereEqualTo(KEY_ID, userId).get()
        )
    }

    override suspend fun signUpUser(user: User): Result<Boolean> {
        return FirebaseFirestore.getInstance()
            .collection(PATH_USER).document(requireNotNull(user.id)).set(user)
            .missionSuccessReturn(true)
    }

    suspend fun <T : Any> T.getResultFrom(source: Task<*>): Result<T?> =
        suspendCoroutine { continuation ->
            source.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    when(val result = task.result){
                        is QuerySnapshot ->{
                            if (result.isEmpty) {
                                continuation.resume(Result.Success(null))
                            } else {
                                continuation.resume(
                                    Result.Success(result.toObjects(this::class.java)[0])
                                )
                            }
                        }
                        is DocumentSnapshot ->{
                            continuation.resume(Result.Success(result.toObject(this::class.java)))
                        }
                    }

                } else {
                    when (val exception = task.exception) {
                        null -> continuation.resume(
                            Result.Fail(Util.getString(R.string.nothing_happen))
                        )
                        else -> {
                            Log.d(TAG,"[${this::class.simpleName}] Error getting documents. ${exception.message}")
                            continuation.resume(Result.Error(exception))
                        }
                    }
                }
            }
        }
    suspend fun Task<*>.missionSuccessReturn(ifSuccess: Boolean): Result<Boolean> =
        suspendCoroutine { continuation ->
            addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(Result.Success(ifSuccess))
                } else {
                    when (val exception = task.exception) {
                        null -> continuation.resume(Result.Fail(Util.getString(R.string.nothing_happen)))
                        else -> {
                            Log.d(TAG,"[${this::class.simpleName}] Error getting documents. ${exception.message}")
                            continuation.resume(Result.Error(exception))
                        }
                    }
                }
            }
        }
}
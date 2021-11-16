package com.weiyung.intotheforest.util

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.weiyung.intotheforest.IntoTheForestApplication
import com.weiyung.intotheforest.database.User

object UserManager {
    private const val USER_DATA = "user_data"
    private const val USER_TOKEN = "user_token"
    private const val USER_ID = "user_id"
    private const val USER_EMAIL = "user_email"
    private const val USER_NAME = "user_name"
    private const val USER_PICTURE = "user_picture"

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user

    var userToken: String? = null
        get() = IntoTheForestApplication.instance
            .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            .getString(USER_TOKEN, null)
        set(value) {
            field = when (value) {
                null -> {
                    IntoTheForestApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .remove(USER_TOKEN)
                        .apply()
                    null
                }
                else -> {
                    IntoTheForestApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .putString(USER_TOKEN, value)
                        .apply()
                    value
                }
            }
        }
    var userID: String? = null
        get() = IntoTheForestApplication.instance
            .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            .getString(USER_ID, null)
        set(value) {
            field = when (value) {
                null -> {
                    IntoTheForestApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .remove(USER_ID)
                        .apply()
                    null
                }
                else -> {
                    IntoTheForestApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .putString(USER_ID, value)
                        .apply()
                    value
                }
            }
        }
    var userEmail: String? = null
        get() = IntoTheForestApplication.instance
            .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            .getString(USER_EMAIL, null)
        set(value) {
            field = when (value) {
                null -> {
                    IntoTheForestApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .remove(USER_EMAIL)
                        .apply()
                    null
                }
                else -> {
                    IntoTheForestApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .putString(USER_EMAIL, value)
                        .apply()
                    value
                }
            }
        }
    var userName: String? = null
        get() = IntoTheForestApplication.instance
            .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            .getString(USER_NAME, null)
        set(value) {
            field = when (value) {
                null -> {
                    IntoTheForestApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .remove(USER_NAME)
                        .apply()
                    null
                }
                else -> {
                    IntoTheForestApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .putString(USER_NAME, value)
                        .apply()
                    value
                }
            }
        }
    var userPicture: String? = null
        get() = IntoTheForestApplication.instance
            .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            .getString(USER_PICTURE, null)
        set(value) {
            field = when (value) {
                null -> {
                    IntoTheForestApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .remove(USER_PICTURE)
                        .apply()
                    null
                }
                else -> {
                    IntoTheForestApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .putString(USER_PICTURE, value)
                        .apply()
                    value
                }
            }
        }

    val isLoggedIn: Boolean
        get() =  userEmail != null
//        get() = userToken != null

    fun clear() {
        userEmail = null
//        userToken = null
        _user.value = null
    }
}
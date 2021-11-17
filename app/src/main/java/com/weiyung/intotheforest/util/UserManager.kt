package com.weiyung.intotheforest.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
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

    var sharedPreferences: SharedPreferences =
        IntoTheForestApplication.instance.applicationContext.getSharedPreferences(
            "userinfo", Context.MODE_PRIVATE)

    fun getUserInfo(user:User){
        sharedPreferences.edit()
            .putString("id",user.id)
            .putString("name",user.name)
            .putString("email",user.email)
            .putString("picture",user.picture)
            .apply()
    }

    fun addUserInfo() : User {
        val id = sharedPreferences.getString("id","")
        val name = sharedPreferences.getString("name","")
        val email = sharedPreferences.getString("email","")
        val picture = sharedPreferences.getString("picture","")
        return User(id!!,name!!,email!!,picture!!)
    }

    val isLoggedIn: Boolean
        get() = sharedPreferences.getString("id",null) != null

}
package com.weiyung.intotheforest.util

import android.content.Context
import android.content.SharedPreferences
import com.weiyung.intotheforest.IntoTheForestApplication
import com.weiyung.intotheforest.database.User

object UserManager {

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
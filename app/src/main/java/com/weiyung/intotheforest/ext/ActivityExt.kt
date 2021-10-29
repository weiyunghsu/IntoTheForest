package com.weiyung.intotheforest.ext

import android.app.Activity
import android.view.Gravity
import android.widget.Toast
import com.weiyung.intotheforest.IntoTheForestApplication
import com.weiyung.intotheforest.factory.ViewModelFactory

fun Activity.getVmFactory(): ViewModelFactory {
    val repository = (applicationContext as IntoTheForestApplication).repository
    return ViewModelFactory(repository)
}

fun Activity?.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}
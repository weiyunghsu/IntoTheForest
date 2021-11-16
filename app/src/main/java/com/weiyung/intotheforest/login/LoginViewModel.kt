package com.weiyung.intotheforest.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.weiyung.intotheforest.database.User
import com.weiyung.intotheforest.database.source.IntoTheForestRepository
import com.weiyung.intotheforest.network.LoadApiStatus
import com.weiyung.intotheforest.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: IntoTheForestRepository) : ViewModel(){

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser: LiveData<FirebaseUser>
        get() = _firebaseUser

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun navigateComplete() {
        _user.value = null
    }

    fun getUser(userId: User?) {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            val result = repository.getUser(userId)
            _user.value = result.handleResultWith(_error, _status)
            Log.i(TAG,"_user.value in getUser: ${_user.value}")
        }
    }

    fun addUser(user: User?) {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING

            val signedIn = user?.let { repository.signUpUser(it).handleResultWith(_error, _status) }
            Log.i(TAG,"$signedIn")
            if (signedIn == true){
                UserManager.userID = user.id
                UserManager.userName = user.name
                UserManager.userEmail = user.email
                UserManager.userPicture = user.picture
                _user.value = user!!
                Log.i(TAG,"_user.value in addUser?????: ${UserManager.userPicture}")
            } else {
                _user.value = null
            }
//            _user.value = if (signedIn == true) user else null

        }
    }

}
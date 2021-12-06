package com.weiyung.intotheforest.database

import androidx.lifecycle.MutableLiveData
import com.weiyung.intotheforest.network.LoadApiStatus
import com.weiyung.intotheforest.util.Util

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Fail(val error: String) : Result<Nothing>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[result=$data]"
            is Fail -> "Fail[error=$error]"
            is Error -> "Error[exception=${exception.message}]"
            Loading -> "Loading"
        }
    }
    fun handleResultWith(
        error: MutableLiveData<String>,
        status: MutableLiveData<LoadApiStatus>
    ): R? {
        return when (this) {
            is Success -> {
                error.value = null
                status.value = LoadApiStatus.DONE
                this.data
            }
            is Fail -> {
                error.value = this.error
                status.value = LoadApiStatus.ERROR
                null
            }
            is Error -> {
                error.value = this.exception.toString()
                status.value = LoadApiStatus.ERROR
                null
            }
            else -> {
                error.value = Util.getString(com.weiyung.intotheforest.R.string.nothingHappen)
                status.value = LoadApiStatus.ERROR
                null
            }
        }
    }
}
val Result<*>.succeeded
    get() = this is Result.Success && data != null

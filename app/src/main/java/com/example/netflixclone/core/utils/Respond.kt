package com.example.netflixclone.core.utils

sealed class Respond<out T>() {
    data class Success<out T>(val data: T) : Respond<T>()
    data class Error(val message: String) : Respond<Nothing>()
    object Loading : Respond<Nothing>()

}

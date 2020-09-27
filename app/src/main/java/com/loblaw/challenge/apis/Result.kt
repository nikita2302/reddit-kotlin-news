package com.loblaw.challenge.apis

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class SuccessNoBody(val success: String) : Result<Nothing>()
    data class ErrorString(val errorString: String) : Result<Nothing>()
}
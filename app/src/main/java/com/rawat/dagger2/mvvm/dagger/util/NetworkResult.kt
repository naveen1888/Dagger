package com.rawat.dagger2.mvvm.dagger.util

sealed class NetWorkResult<out T>(val status: ApiStatus, val data: T?, val message: String?) {

    data class Success<out T>(val success: T?) :
        NetWorkResult<T>(status = ApiStatus.SUCCESS, data = success, message = null)

    data class Error<out T>(val failure: T?, val exception: String) :
        NetWorkResult<T>(status = ApiStatus.ERROR, data = failure, message = exception)

    data class Loading<out T>(val isLoading: Boolean) :
        NetWorkResult<T>(status = ApiStatus.LOADING, data = null, message = null)
}

enum class ApiStatus {
    SUCCESS,
    ERROR,
    LOADING
}
package com.rawat.dagger2.mvvm.dagger.viewmodels

import okhttp3.ResponseBody

sealed class Response<out T> {

    data object Loading : Response<Nothing>()

    data class Success<out T>(
        val data: T?
    ) : Response<T>()


    data class Failure(
        val e: ResponseBody?
    ) : Response<Nothing>()

}
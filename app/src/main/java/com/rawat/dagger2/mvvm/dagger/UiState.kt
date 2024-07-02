package com.rawat.dagger2.mvvm.dagger

sealed class UiState<out T> {
    data class Success<T : Any>(val data: T?) : UiState<T>()
    //data class ErrorMessage(val message: String) : UiState<Nothing>()
    data class Error<T>(val message: T) : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
}
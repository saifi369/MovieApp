package com.starzplay.movieapp.domain

/**
 * Data state for processing api response Loading, Success and Error
 */
sealed class DataState<T>(val data: T? = null, val error: String = "") {
    class Success<T>(data: T?) : DataState<T>(data)
    class Loading<T> : DataState<T>()
    class Error<T>(message: String) : DataState<T>(error = message)
}
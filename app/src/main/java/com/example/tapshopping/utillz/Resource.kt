package com.example.tapshopping.utillz

sealed class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null,
) {

    class Success<T>(data: T) : Resource<T>(data)
    class Loading: Resource<Nothing>()
    class Error<T>(error: Throwable, data: T? = null) : Resource<T>(data, error)
}
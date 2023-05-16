package com.example.tapshopping.utillz

import com.example.tapshopping.data.model.AdminAuthResponse

sealed class Resource<T>(
    val data: T? = null,
    val error: String? = null,
) {

    class Success<T>(data: T?) : Resource<T>(data)
    class Loading: Resource<Nothing>()
    class Error<T>(message: String) : Resource<T>(error = message)
}
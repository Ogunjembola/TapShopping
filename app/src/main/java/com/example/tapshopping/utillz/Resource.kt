package com.example.tapshopping.utillz

import com.example.tapshopping.data.model.ErrorResponse

sealed class Resource<T>(
    val data: T? = null,
    val error: String? = null,
) {

    class Success<T>(data: T?) : Resource<T>(data)
    class Loading: Resource<Nothing>()
    class Error<T>(error: String? = null, data: T? = null) : Resource<T>(data, error)
}
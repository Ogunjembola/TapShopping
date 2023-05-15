package com.example.tapshopping.data.model

import com.google.gson.annotations.SerializedName

data class AdminAuthResponse(
    val content: AuthResponse,
    @SerializedName("error")
    val errorResponse: ErrorResponse,
    val success: Success
)

data class AuthResponse(
    @SerializedName("data")
    val adminResponseData: AdminResponseData
)

data class AdminResponseData(
    val token: String? = null
)

data class ErrorResponse(
    val code: Int,
    val message: String,
    val status: Int
)

data class Success(
    val code: Int,
    val message: String,
    val status: Int
)
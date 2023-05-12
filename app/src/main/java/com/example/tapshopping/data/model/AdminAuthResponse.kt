package com.example.tapshopping.data.model

import com.google.gson.annotations.SerializedName

data class AdminAuthResponse(
    val content: AuthResponse,
    val error: Error,
    val success: ErrorSuccess
)

data class AuthResponse(
    @SerializedName("data")
    val adminResponseData: AdminResponseData
)

data class AdminResponseData(
    val token: String? = null
)

data class Error(
    val code: Int,
    val message: String,
    val status: Int
)

data class Success(
    val code: Int,
    val message: String,
    val status: Int
)
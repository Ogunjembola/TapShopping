package com.example.tapshopping.data.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("content")
    val errorContent: ErrorContent,
    val error: Error,
    @SerializedName("success")
    val errorSuccess: ErrorSuccess
)

data class ErrorSuccess(
    val code: Int,
    val message: String,
    val status: Int
)

data class ErrorContent(
    val `data`: Data
)
package com.example.tapshopping.data.model

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("content")
    val content: Content,
    @SerializedName("error")
    val error: Error,
    @SerializedName("success")
    val success: Success
) {
    data class Content(
        @SerializedName("data")
        val data: Data
    ) {
        data class Data(
            val token:String
        )
    }

    data class Error(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Int
    )

    data class Success(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Int
    )
}
package com.example.tapshopping.data.model

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("data")
    val loginData: LoginData
)

data class LoginData(
    @SerializedName("password")
    val password: String?,
    @SerializedName("username")
    val username: String?
)
package com.example.tapshopping.data.model

import com.google.gson.annotations.SerializedName

data class AdminLogin(
    val adminLoginData: AdminLoginData
)

data class AdminLoginData(
    val password: String,
    val username: String
)
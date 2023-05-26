package com.example.tapshopping.data.model

import com.google.gson.annotations.SerializedName

data class Registration(
    @SerializedName("data")
    val registerData: RegisterData
)

data class RegisterData(
    val email: String,
    val name: String,
    val password: String,
    val username: String
)
package com.example.tapshopping.data.model

import com.google.gson.annotations.SerializedName

data class CreateAdmin(
    @SerializedName("data")
    val createAdminData: CreateAdminData
)

data class CreateAdminData(
    val email: String,
    val name: String,
    val password: String,
    val username: String
)
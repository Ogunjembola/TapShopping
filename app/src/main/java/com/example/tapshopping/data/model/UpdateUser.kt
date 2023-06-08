package com.example.tapshopping.data.model

import com.google.gson.annotations.SerializedName

data class UpdateUser(
    @SerializedName("data")
    val updateUserData: UpdateUserData
)

data class UpdateUserData(
    val name: String,
    val username: String,
    val password: String
)
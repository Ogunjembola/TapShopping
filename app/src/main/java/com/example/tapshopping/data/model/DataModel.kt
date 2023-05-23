package com.example.tapshopping.data.model

import com.google.gson.annotations.SerializedName

data class DataModel(
    @SerializedName("data")
    val userRegistrationData: UserRegistrationData
)

data class UserRegistrationData(
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("username")
    val username: String?
)

data class GetUserData(
    @SerializedName("data")
    val userLoginData: UserLoginData
)

data class UserLoginData(
    @SerializedName("password")
    val password: String?,
    @SerializedName("username")
    val username: String?
)
package com.example.tapshopping.data.model

import com.google.gson.annotations.SerializedName

data class GetUserResponse(
    val error: Error,
    val success: Success,
    @SerializedName("content")
    val userData: UserData
)

data class Address(
    val geolocation: Geolocation
)

data class Geolocation(
    @SerializedName("lat")
    val latitude: Int,
    @SerializedName("long")
    val longitude: Int
)

data class UserData(
    val data: Data
)

data class Data(
    val user: User
)

data class User(
    val __v: Int,
    @SerializedName("_id")
    val userId: String,
    val address: Address,
    val createdAt: String,
    val email: String,
    val name: String,
    val password: String,
    val updatedAt: String,
    val username: String
)
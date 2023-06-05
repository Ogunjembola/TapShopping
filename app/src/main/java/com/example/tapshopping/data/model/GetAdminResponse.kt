package com.example.tapshopping.data.model

import com.google.gson.annotations.SerializedName

data class GetAdminResponse(
    @SerializedName("error")
    val error: Error,
    @SerializedName("success")
    val success: Success,
    @SerializedName("content")
    val adminData: AdminData
)

data class AdminData(
    @SerializedName("data")
    val dataResponse: DataResponse
)

data class DataResponse(
    @SerializedName("admin")
    val admin: Admin
)
data class Admin(
    val __v: Int,
    @SerializedName("_id")
    val adminId: String,
    val createdAt: String,
    val email: String,
    @SerializedName("name")
    val fullName: String,
    val password: String,
    val updatedAt: String,
    val username: String
)
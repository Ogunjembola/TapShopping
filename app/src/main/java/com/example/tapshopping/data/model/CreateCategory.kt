package com.example.tapshopping.data.model

import com.google.gson.annotations.SerializedName

data class CreateCategory(
    @SerializedName("data")
    val createCategoryData: CreateCategoryData
)

data class CreateCategoryData(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String
)
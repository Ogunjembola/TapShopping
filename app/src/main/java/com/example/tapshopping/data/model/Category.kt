package com.example.tapshopping.data.model

import com.example.tapshopping.R
import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("data")
    val categoryData: CategoryData
)

data class CategoryData(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String
)
package com.example.tapshopping.data.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("error")
    val error: Error,
    @SerializedName("success")
    val success: Success,
    @SerializedName("content")
    val categoryContent: CategoryContent
)

data class CategoryContent(
@SerializedName("data")
val categories:ArrayList<CategoryData>
)

data class CategoryData(
    @SerializedName("_id")
    val categoryId: String,
    @SerializedName("name")
    val categoryName: String,
    @SerializedName("description")
    val categoryDescription: String,
    var isSelected:Boolean = false
)
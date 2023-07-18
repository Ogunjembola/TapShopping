package com.example.tapshopping.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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
@Parcelize
data class CategoryData(
    @SerializedName("_id")
    val categoryId: String,
    @SerializedName("name")
    val categoryName: String,
    @SerializedName("description")
    val categoryDescription: String,
    var isSelected:Boolean = false
):Parcelable
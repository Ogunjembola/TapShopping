package com.example.tapshopping.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CartResponse(

    @SerializedName("content")
val content: CartContent,
)

data class CartContent(
    @SerializedName("data")
    val data: CartData
)

data class CartData(
    val __v: Int,
    val _id: String,
    val cartPrice: Int,
    val createdAt: String,
    val products: List<CartProduct>,
    val updatedAt: String,
    val userID: String
)

data class CartProduct(
    val _id: String,
    val basePrice: Int,
    val productID: CartProductID,
    val quantity: Int,
    val totalPrice: Int
)

data class CartProductID(
    val __v: Int,
    val _id: String,
    val averageRating: Int,
    val categoryID: String,
    val createdAt: String,
    val description: String,
    val discount: Int,
    val images: List<Any>,
    val inStock: Boolean,
    val name: String,
    val noOfRatings: Int,
    val noOfReviews: Int,
    val price: Int,
    val quantity: Int,
    val ratings: List<Any>,
    val reviews: List<Any>,
    val updatedAt: String,
    val variant: List<CartVariant>
)
data class CartVariant(
    val _id: String,
    val colour: List<String>,
    val images: List<String>,
    val size: List<String>
)


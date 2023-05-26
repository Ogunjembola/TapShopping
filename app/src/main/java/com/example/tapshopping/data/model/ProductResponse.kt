package com.example.tapshopping.data.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    val content: AuthResponse,
    val error: Error,
    val success: AuthResponse.Success
)

data class Content(
    @SerializedName("data")
    val products: List<Product>
)

data class Product(
    val __v: Int,
    @SerializedName("_id")
    val productId: String,
    val averageRating: Int,
    val categoryID: String,
    val createdAt: String,
    val description: String,
    val discount: Int,
    val images: List<String>,
    val inStock: Boolean,
    val name: String,
    val noOfRatings: Int,
    val noOfReviews: Int,
    val price: Int,
    val quantity: Int,
    val ratings: List<Rating>,
    val reviews: List<Review>,
    val updatedAt: String,
    val variant: List<Variant>
)

data class Rating(
    val _id: String,
    val rate: Int,
    val userID: String
)

data class Review(
    val _id: String,
    val comment: String,
    val images: List<String>,
    val userID: String
)
data class Variant(
    val _id: String,
    val colour: List<String>,
    val images: List<String>,
    val size: List<String>
)
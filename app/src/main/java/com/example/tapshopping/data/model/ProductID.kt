package com.example.tapshopping.data.model

data class ProductID(
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
    val variant: List<Variant>
)
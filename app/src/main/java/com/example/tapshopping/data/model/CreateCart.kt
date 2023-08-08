package com.example.tapshopping.data.model


import com.google.gson.annotations.SerializedName

data class CreateCart(
    @SerializedName("data")
    val createCartData: CreateCartData
)

data class CreateCartData(
    @SerializedName("products")
    val products: List<CartProductData>,
    @SerializedName("user")
    val user: String
)

data class CartProductData(
    @SerializedName("product")
    val productID: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("basePrice")
    val basePrice: String,
    @SerializedName("totalPrice")
    val totalPrice: String
)


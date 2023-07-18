package com.example.tapshopping.data.model

import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("data")
    val data: CartData
)
data class CartData(
    @SerializedName("products")
    val products: List<CartProduct>,
    @SerializedName("userID")
    val userID: String? = null
)
data class CartProduct(
    @SerializedName("basePrice")
    val basePrice: Int? = null,
    @SerializedName("productID")
    val productID: String? = null,
    @SerializedName("quantity")
    val quantity: Int? = null,
    @SerializedName("totalPrice")
    val totalPrice: Int? = null
)

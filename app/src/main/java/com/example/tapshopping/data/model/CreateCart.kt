package com.example.tapshopping.data.model


import com.google.gson.annotations.SerializedName

data class CreateCart(
    @SerializedName("data")
    val createCartData: CreateCartData
)
    data class CreateCartData(
        @SerializedName("products")
        val products: List<CartProductData>,
        @SerializedName("userID")
        val userID: String
    )
        data class CartProductData(
            @SerializedName("productID")
            val productID: String,
            @SerializedName("quantity")
            val quantity: Int
        )


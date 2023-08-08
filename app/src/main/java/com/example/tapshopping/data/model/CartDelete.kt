package com.example.tapshopping.data.model


import com.google.gson.annotations.SerializedName

data class CartDelete(
    @SerializedName("data")
    val data: Data
) {
    data class Data(
        @SerializedName("products")
        val products: List<Product>,
        @SerializedName("userID")
        val userID: String
    ) {
        data class Product(
            @SerializedName("productID")
            val productID: String,
            @SerializedName("quantity")
            val quantity: Int
        )
    }
}
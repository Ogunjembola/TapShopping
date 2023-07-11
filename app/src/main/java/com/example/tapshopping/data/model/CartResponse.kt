package com.example.tapshopping.data.model


import com.google.gson.annotations.SerializedName

data class CartResponse(
    @SerializedName("content")
    val content: CartContent,
    @SerializedName("error")
    val error: Error,
    @SerializedName("success")
    val success: Success
)
data class CartContent(
    @SerializedName("data")
    val data: List<CartProduct>
)
data class ResponseDataClass(val name: String)




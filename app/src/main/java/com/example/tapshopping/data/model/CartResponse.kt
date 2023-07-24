package com.example.tapshopping.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CartResponse(
    @SerializedName("content")
    val content: CartContent,
    @SerializedName("error")
    val error: Error,
    @SerializedName("success")
    val success: Success,
     // Merge CartData with CartResponse
)

data class CartContent(
    @SerializedName("data")
val data: CartData
)

data class CartData(
    @SerializedName("products")
    val products: List<CartProduct>,
    @SerializedName("userID")
    val userID: String? = null
)

@Parcelize
data class CartProduct(
    @SerializedName("basePrice")
    val basePrice: Int? = null,
    @SerializedName("productID")
    val productID: String? = null,
    @SerializedName("quantity")
    val quantity: Int? = null,
    @SerializedName("totalPrice")
    val totalPrice: Int? = null
) : Parcelable


data class ResponseDataClass(val name: String)

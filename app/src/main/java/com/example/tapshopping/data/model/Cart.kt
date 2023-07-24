//package com.example.tapshopping.data.model
//
//import android.os.Parcelable
//import com.google.gson.annotations.SerializedName
//import kotlinx.parcelize.Parcelize
//
//data class Cart(
//    @SerializedName("data")
//    val data: CartData
//)
//data class CartData(
//    @SerializedName("products")
//    val products: List<CartProduct>,
//    @SerializedName("userID")
//    val userID: String? = null
//)
//@Parcelize
//data class CartProduct(
//    @SerializedName("basePrice")
//    val basePrice: Int? = null,
//    @SerializedName("productID")
//    val productID: String? = null,
//    @SerializedName("quantity")
//    val quantity: Int? = null,
//    @SerializedName("totalPrice")
//    val totalPrice: Int? = null
//): Parcelable

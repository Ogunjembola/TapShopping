package com.example.tapshopping.data.model

import com.google.gson.annotations.SerializedName

data class CreateProduct(
@SerializedName("data")
val productData: CreateProductData
)

data class CreateProductData(
    @SerializedName("name")
    val productName: String,
    @SerializedName("description")
    val productDescription: String,
    @SerializedName("price")
    val productPrice: Int,
    @SerializedName("discount")
    val discountedPrice: Int,
    @SerializedName("files")
    val productImages: List<String>,
    @SerializedName("variant")
    val productVariant: ProductVariant? = null,
    @SerializedName("categoryID")
    val categoryId: String,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("inStock")
    val inStock: Boolean


)
data class ProductVariant(
    @SerializedName("size")
    val size: String,
    @SerializedName("colour")
    val colour: String,
    @SerializedName("images")
    val images: List<String>
)
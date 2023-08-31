package com.example.tapshopping.data.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class CartResponse (
    @SerializedName("content")
    @Expose
    var content: CartContent? = null
)
    data class CartContent (
        @SerializedName("data")
        @Expose
        var data: CartData? = null
    )
        data class CartData (
            @SerializedName("_id")
            @Expose
            var id: String? = null,

            @SerializedName("user")
            @Expose
            var user: String? = null,

            @SerializedName("products")
            @Expose
            var products: List<CartProduct>? = null,

            @SerializedName("cartPrice")
            @Expose
            var cartPrice: Double? = null,

            @SerializedName("createdAt")
            @Expose
            var createdAt: String? = null,

            @SerializedName("updatedAt")
            @Expose
            var updatedAt: String? = null,

            @SerializedName("__v")
            @Expose
            var v: Int? = null
        )
           data class CartProduct (
                @SerializedName("product")
                @Expose
                var product: Product__1? = null,

                @SerializedName("quantity")
                @Expose
                var quantity: Int? = null,

                @SerializedName("basePrice")
                @Expose
                var basePrice: Double? = null,

                @SerializedName("totalPrice")
                @Expose
                var totalPrice: Double? = null,

                @SerializedName("_id")
                @Expose
                var id: String? = null
            ): Serializable
               data class Product__1 (
                   @SerializedName("_id")
                   @Expose
                   var id: String? = null,

                   @SerializedName("name")
                   @Expose
                   var name: String? = null,

                   @SerializedName("description")
                   @Expose
                   var description: String? = null,

                   @SerializedName("price")
                   @Expose
                   var price: Double? = null,

                   @SerializedName("discount")
                   @Expose
                   var discount: Double? = null,

                   @SerializedName("discountedPrice")
                   @Expose
                   var discountedPrice: Double? = null,

                   @SerializedName("images")
                   @Expose
                   var images: List<Any>? = null,

                   @SerializedName("noOfRatings")
                   @Expose
                   var noOfRatings: Int? = null,

                   @SerializedName("averageRating")
                   @Expose
                   var averageRating: Int? = null,

                   @SerializedName("ratings")
                   @Expose
                   var ratings: List<Any>? = null,

                   @SerializedName("noOfReviews")
                   @Expose
                   var noOfReviews: Int? = null,

                   @SerializedName("reviews")
                   @Expose
                   var reviews: List<Any>? = null,

                   @SerializedName("variant")
                   @Expose
                   var variant: List<CartVariant>? = null,

                   @SerializedName("category")
                   @Expose
                   var category: String? = null,

                   @SerializedName("quantity")
                   @Expose
                   var quantity: Int? = null,

                   @SerializedName("inStock")
                   @Expose
                   var inStock: Boolean? = null,

                   @SerializedName("createdAt")
                   @Expose
                   var createdAt: String? = null,

                   @SerializedName("updatedAt")
                   @Expose
                   var updatedAt: String? = null,

                   @SerializedName("__v")
                   @Expose
                   var v: Int? = null
               )
                  data class CartVariant (
                       @SerializedName("size")
                       @Expose
                       var size: List<String>? = null,

                       @SerializedName("colour")
                       @Expose
                       var colour: List<String>? = null,

                       @SerializedName("images")
                       @Expose
                       var images: List<String>? = null,

                       @SerializedName("_id")
                       @Expose
                       var id: String? = null,
                  )


package com.example.tapshopping.data.model

import java.io.Serializable


data class Product(

    val user_id: String = "",
    val user_name: String = "",
    val title: String = "",
    val price: String = "",
    val description: String = "",
    val stock_quantity: String = "",
    val image: Int = "",
    var product_id: String = "",
) : Serializable


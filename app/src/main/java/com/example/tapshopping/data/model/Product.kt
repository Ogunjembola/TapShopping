package com.example.tapshopping.data.model

import com.example.tapshopping.R
import java.io.Serializable


data class Product(

    val user_id: String = "1",
    val user_name: String = "Touch and Pay",
    val title: String = "Executive bag",
    val price: String = "7,000",
    val description: String = "This bag is made for executive use",
    val stock_quantity: String = "4",
    val image: Int = R.drawable.image36,
    var product_id: String = "2",
) : Serializable


package com.example.tapshopping.data.model

import com.example.tapshopping.R

val products = listOf(
    Product(),
    Product(),
    Product(),
    Product(),
    Product(),
    Product(),
    Product(),
    Product(),
    Product(),
    Product(),
    Product(),
    Product(),
    Product()
)
data class Category(val catImage:Int = R.drawable.image36, val catName:String = "Household items")

val categories = listOf(
    Category(),
    Category(),
    Category(),
    Category(),
    Category(),
    Category(),
    Category()
)
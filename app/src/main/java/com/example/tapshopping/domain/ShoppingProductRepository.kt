package com.example.tapshopping.domain

import com.example.tapshopping.data.model.AuthResponse
import com.example.tapshopping.data.model.CreateProduct
import com.example.tapshopping.data.model.ProductResponse
import com.example.tapshopping.utillz.Resource
import kotlinx.coroutines.flow.Flow


interface ShoppingProductRepository {
   suspend fun createProduct(token: String, createProduct: CreateProduct ): Flow<Resource<AuthResponse>>

   suspend fun getProduct(token: String): Flow<Resource<ProductResponse>>
   suspend fun getAProduct(token: String): Flow<Resource<ProductResponse>>
}
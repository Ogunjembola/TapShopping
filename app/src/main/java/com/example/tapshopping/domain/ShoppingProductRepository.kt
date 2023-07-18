package com.example.tapshopping.domain

import com.example.tapshopping.data.model.AuthResponse
import com.example.tapshopping.data.model.CreateProduct
import com.example.tapshopping.utillz.Resource
import kotlinx.coroutines.flow.Flow


interface ShoppingProductRepository {
   suspend fun createProduct(token: String, createProduct: CreateProduct ): Flow<Resource<AuthResponse>>
}
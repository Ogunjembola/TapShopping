package com.example.tapshopping.domain

import com.example.tapshopping.data.model.Cart
import com.example.tapshopping.data.model.CartResponse
import com.example.tapshopping.utillz.Resource
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun createCart(createCart: Cart, token: String): Flow<Resource<CartResponse>>
    suspend fun getCart(token: String): Flow<Resource<CartResponse>>
    suspend fun deleteCart(token: String, uuid: String): Flow<Resource<CartResponse>>
}
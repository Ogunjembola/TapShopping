package com.example.tapshopping.domain

import com.example.tapshopping.data.model.AuthResponse
import com.example.tapshopping.data.model.Product
import com.example.tapshopping.data.model.ProductResponse
import com.example.tapshopping.data.service.NetworkService
import com.example.tapshopping.utillz.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface ProductRepository {

    suspend fun getProduct(token: String): Flow<Resource<ProductResponse>>
    suspend fun getAProduct(token: String): Flow<Resource<ProductResponse>>
}

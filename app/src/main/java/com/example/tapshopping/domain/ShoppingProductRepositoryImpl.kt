package com.example.tapshopping.domain

import com.example.tapshopping.core.di.CoroutineModule
import com.example.tapshopping.data.model.AuthResponse
import com.example.tapshopping.data.model.CreateProduct
import com.example.tapshopping.data.service.NetworkService
import com.example.tapshopping.utillz.Resource
import com.example.tapshopping.utillz.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ShoppingProductRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    @CoroutineModule.IoDispatcher private val dispatcher: CoroutineDispatcher
) : ShoppingProductRepository {

    override suspend fun createProduct(
        token: String,
        createProduct: CreateProduct
    ): Flow<Resource<AuthResponse>> {
        return withContext(dispatcher) {
            return@withContext flow {
                emit(safeApiCall {
                    networkService.createProduct(token = token, productData = createProduct)
                })
            }
        }
    }
}
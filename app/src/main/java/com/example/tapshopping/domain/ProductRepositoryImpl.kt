package com.example.tapshopping.domain

import com.example.tapshopping.core.di.CoroutineModule
import com.example.tapshopping.data.model.AuthResponse
import com.example.tapshopping.data.model.Product
import com.example.tapshopping.data.model.ProductResponse
import com.example.tapshopping.data.service.NetworkService
import com.example.tapshopping.utillz.Resource
import com.example.tapshopping.utillz.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    @CoroutineModule.IoDispatcher private val dispatcher: CoroutineDispatcher
) : ProductRepository {
    private val flowable = Dispatchers.IO
    override suspend fun getProduct(token: String): Flow<Resource<ProductResponse>> {
        return withContext(dispatcher) {
            return@withContext flow<Resource<ProductResponse>> {
                emit(safeApiCall {
                    networkService.getProducts(token)
                })
            }

        }.flowOn(flowable)

    }

    override suspend fun getAProduct(token: String): Flow<Resource<ProductResponse>> {
        return withContext(dispatcher) {
            return@withContext flow<Resource<ProductResponse>> {
                emit(safeApiCall {
                    networkService.getAProducts(token)
                })
            }
        }
    }
}

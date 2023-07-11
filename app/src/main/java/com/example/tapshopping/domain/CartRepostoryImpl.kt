package com.example.tapshopping.domain

import com.example.tapshopping.core.di.CoroutineModule
import com.example.tapshopping.data.model.Cart
import com.example.tapshopping.data.model.CartResponse
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

class CartRepostoryImpl @Inject constructor(
    private  val networkService: NetworkService,@CoroutineModule.IoDispatcher private val dispatcher : CoroutineDispatcher
): CartRepository {
    private val flowable = Dispatchers.IO

    override suspend fun createCart(createCart: Cart, token: String): Flow<Resource<CartResponse>> =
        withContext(dispatcher){
            return@withContext flow<Resource<CartResponse>> {
                emit(safeApiCall {
                    networkService.createCart(createCart,token)
                })
            }.flowOn(dispatcher)
        }

    override suspend fun getCart(token: String): Flow<Resource<CartResponse>> =
        withContext(dispatcher){
            return@withContext flow<Resource<CartResponse>> {
                emit(safeApiCall {
                    networkService.getCart(token)
                })
            }.flowOn(dispatcher)
        }
    override suspend fun deleteCart(token: String, uuid: String): Flow<Resource<CartResponse>> =
        withContext(dispatcher){
            return@withContext flow<Resource<CartResponse>> {
                emit(safeApiCall {
                    networkService.deleteCart(token)
                })
            }
        }
}
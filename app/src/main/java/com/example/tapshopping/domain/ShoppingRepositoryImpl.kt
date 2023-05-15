package com.example.tapshopping.domain

import com.example.tapshopping.core.di.CoroutineModule
import com.example.tapshopping.data.model.AdminAuthResponse
import com.example.tapshopping.data.model.CreateAdmin
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

class ShoppingRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    @CoroutineModule.IoDispatcher private val dispatcher: CoroutineDispatcher
) :
    ShoppingRepository {
    private val flowable = Dispatchers.IO

    override suspend fun createAdmin(createAdmin: CreateAdmin): Flow<Resource<AdminAuthResponse>> =
        withContext(dispatcher) {
            return@withContext flow<Resource<AdminAuthResponse>> {
                emit(safeApiCall { networkService.createAdmin(createAdmin) })
            }
        }.flowOn(flowable)
}


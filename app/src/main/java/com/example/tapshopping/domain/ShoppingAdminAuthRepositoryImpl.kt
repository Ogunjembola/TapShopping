package com.example.tapshopping.domain

import com.example.tapshopping.core.di.CoroutineModule
import com.example.tapshopping.data.model.*
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

class ShoppingAdminAuthRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    @CoroutineModule.IoDispatcher private val dispatcher: CoroutineDispatcher
) : ShoppingAdminAuthRepository{

    private val flowable = Dispatchers.IO

    override suspend fun createAdmin(createAdmin: Registration): Flow<Resource<AuthResponse>> =
        withContext(dispatcher) {
            return@withContext flow<Resource<AuthResponse>> {
                emit(safeApiCall { networkService.createAdmin(createAdmin) })
            }
        }.flowOn(flowable)

    override suspend fun getAdminData(token: String): Flow<Resource<GetAdminResponse>> {
        return withContext(dispatcher) {
            return@withContext flow {
                emit(safeApiCall { networkService.getAdmin(token) })
            }
        }.flowOn(flowable)
    }

    override suspend fun updateAdminData(
        token: String,
        updateUser: UpdateUser
    ): Flow<Resource<AuthResponse>> =
        withContext(dispatcher) {
            return@withContext flow {
                emit(safeApiCall {
                    networkService.updateAdmin(token = token, updateUser = updateUser)
                })
            }
        }.flowOn(flowable)

    override suspend fun loginAdmin(loginAdmin: Login): Flow<Resource<AuthResponse>> =
        withContext(dispatcher) {
            return@withContext flow {
                emit(safeApiCall {
                    networkService.adminLogin(loginAdmin)
                })
            }
        }.flowOn(flowable)


}
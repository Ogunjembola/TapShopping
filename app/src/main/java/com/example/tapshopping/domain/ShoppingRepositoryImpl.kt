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

class ShoppingRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    @CoroutineModule.IoDispatcher private val dispatcher: CoroutineDispatcher
) : ShoppingRepository {

    private val flowable = Dispatchers.IO

    override suspend fun createAdmin(createAdmin: Registration): Flow<Resource<AuthResponse>> =
        withContext(dispatcher) {
            return@withContext flow<Resource<AuthResponse>> {
                emit(safeApiCall { networkService.createAdmin(createAdmin) })
            }
        }.flowOn(flowable)

    override suspend fun createUser(createUser: Registration): Flow<Resource<AuthResponse>> =
        withContext(dispatcher) {
            return@withContext flow<Resource<AuthResponse>> {
                emit(safeApiCall { networkService.createUser(createUser) })
            }.flowOn(flowable)
        }

    override suspend fun getUser(userLogin: Login): Flow<Resource<AuthResponse>> =
        withContext(dispatcher) {
            return@withContext flow<Resource<AuthResponse>> {
                emit(safeApiCall { networkService.getRegisteredUsers(userLogin) })
            }.flowOn(flowable)
        }

    override suspend fun getUserData(token: String): Flow<Resource<GetUserResponse>> {
        return withContext(dispatcher) {
            return@withContext flow<Resource<GetUserResponse>> {
                emit(safeApiCall { networkService.getUser(token) })
            }
        }.flowOn(flowable)
    }

    override suspend fun getAdminData(token: String): Flow<Resource<GetAdminResponse>> {
        return withContext(dispatcher) {
            return@withContext flow {
                emit(safeApiCall { networkService.getAdmin(token) })
            }
        }.flowOn(flowable)
    }

    override suspend fun loginAdmin(loginAdmin: Login): Flow<Resource<AuthResponse>> =
        withContext(dispatcher) {
            return@withContext flow {
                emit(safeApiCall {
                    networkService.adminLogin(loginAdmin)
                })
            }
        }.flowOn(flowable)

}


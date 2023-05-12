package com.example.tapshopping.domain

import com.example.tapshopping.data.model.CreateAdmin
import com.example.tapshopping.data.service.NetworkService
import com.example.tapshopping.domain.ShoppingRepository
import com.example.tapshopping.utillz.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ShoppingRepositoryImpl @Inject constructor( private val networkService: NetworkService) :
    ShoppingRepository {
    private val flowable = Dispatchers.IO

    override suspend fun createAdmin(createAdmin: CreateAdmin) = flow {
        emit(Resource.Loading())
        try {
            val response = networkService.createAdmin(createAdmin)
            if (response.isSuccessful)
                emit(Resource.Success(response.body()!!))
            else {
                val error = response.errorBody()?.string()
                emit(Resource.Error(error = Throwable(error), data = response.body()))
            }
        } catch (e: Throwable) {
            emit(Resource.Error(e))
        }
    }.flowOn(flowable)

}
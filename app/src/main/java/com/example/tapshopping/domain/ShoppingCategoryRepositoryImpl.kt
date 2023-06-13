package com.example.tapshopping.domain

import com.example.tapshopping.core.di.CoroutineModule
import com.example.tapshopping.data.model.AuthResponse
import com.example.tapshopping.data.model.Category
import com.example.tapshopping.data.service.NetworkService
import com.example.tapshopping.utillz.Resource
import com.example.tapshopping.utillz.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShoppingCategoryRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    @CoroutineModule.IoDispatcher private val dispatcher: CoroutineDispatcher
) : ShoppingCategoryRepository {

    override suspend fun createCategories(category: Category, token: String): Flow<Resource<AuthResponse>> =
        withContext(dispatcher){
            return@withContext flow {
                emit(safeApiCall {
                    networkService.createCategory(category = category, token = token)
                })
            }
        }

    override suspend fun updateCategories(category: Category): Flow<Resource<AuthResponse>> {
        TODO("Not yet implemented")
    }
}
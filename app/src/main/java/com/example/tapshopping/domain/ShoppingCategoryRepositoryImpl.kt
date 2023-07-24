package com.example.tapshopping.domain

import com.example.tapshopping.core.di.CoroutineModule
import com.example.tapshopping.data.model.AuthResponse
import com.example.tapshopping.data.model.Category
import com.example.tapshopping.data.model.CreateCategory
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

class ShoppingCategoryRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    @CoroutineModule.IoDispatcher private val dispatcher: CoroutineDispatcher
) : ShoppingCategoryRepository {
    private val flowable = Dispatchers.IO
    override suspend fun createCategories(
        category: CreateCategory,
        token: String
    ): Flow<Resource<AuthResponse>> =
        withContext(dispatcher) {
            return@withContext flow {
                emit(safeApiCall {
                    networkService.createCategory(category = category, token = token)
                })
            }
        }

    override suspend fun getCategories(): Flow<Resource<Category>> {
        return withContext(dispatcher) {
            return@withContext flow<Resource<Category>> {
                emit(safeApiCall {
                    networkService.getCategories()
                })
            }
        }.flowOn(flowable)
    }

    override suspend fun deleteCategory(
        token: String,
        categoryId: String
    ): Flow<Resource<AuthResponse>> {
        return withContext(dispatcher) {
            return@withContext flow {
                emit(safeApiCall {
                    networkService.deleteCategory(token = token, categoryId = categoryId)
                })
            }
        }.flowOn(flowable)
    }

    override suspend fun updateCategory(
        token: String,
        categoryId: String,
        category: CreateCategory
    ): Flow<Resource<AuthResponse>> {
        return withContext(dispatcher){
            return@withContext flow {
                emit(safeApiCall {
                    networkService.updateCategory(token = token, categoryId = categoryId, category = category)
                })
            }
        }
    }
}
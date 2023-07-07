package com.example.tapshopping.domain

import com.example.tapshopping.data.model.AuthResponse
import com.example.tapshopping.data.model.Category
import com.example.tapshopping.data.model.CreateCategory
import com.example.tapshopping.utillz.Resource
import kotlinx.coroutines.flow.Flow

interface ShoppingCategoryRepository {

    suspend fun createCategories(
        category: CreateCategory,
        token: String
    ): Flow<Resource<AuthResponse>>

    suspend fun getCategories(): Flow<Resource<Category>>

    suspend fun deleteCategory(token: String, categoryId: String): Flow<Resource<AuthResponse>>
    suspend fun updateCategory(
        token: String,
        categoryId: String,
        category: CreateCategory
    ): Flow<Resource<AuthResponse>>
}
package com.example.tapshopping.domain

import com.example.tapshopping.data.model.AuthResponse
import com.example.tapshopping.data.model.Category
import com.example.tapshopping.utillz.Resource
import kotlinx.coroutines.flow.Flow

interface ShoppingCategoryRepository {

    suspend fun createCategories(category: Category, token: String): Flow<Resource<AuthResponse>>

    suspend fun updateCategories(category: Category): Flow<Resource<AuthResponse>>
}
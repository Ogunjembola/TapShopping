package com.example.tapshopping.domain

import com.example.tapshopping.data.model.AdminAuthResponse
import com.example.tapshopping.data.model.CreateAdmin
import com.example.tapshopping.utillz.Resource
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
    suspend fun createAdmin(createAdmin: CreateAdmin):Flow<Resource<out AdminAuthResponse>>
}
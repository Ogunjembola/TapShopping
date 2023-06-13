package com.example.tapshopping.domain

import com.example.tapshopping.data.model.*
import com.example.tapshopping.utillz.Resource
import kotlinx.coroutines.flow.Flow

interface ShoppingAdminAuthRepository {

    suspend fun getAdminData(token: String): Flow<Resource<GetAdminResponse>>

    suspend fun updateAdminData(token: String, updateUser: UpdateUser): Flow<Resource<AuthResponse>>

    suspend fun createAdmin(createAdmin: Registration):Flow<Resource<AuthResponse>>

    suspend fun loginAdmin(loginAdmin: Login):Flow<Resource<AuthResponse>>
}
package com.example.tapshopping.domain


import com.example.tapshopping.data.model.AuthResponse
import com.example.tapshopping.data.model.GetUserResponse
import com.example.tapshopping.data.model.Login
import com.example.tapshopping.data.model.Registration
import com.example.tapshopping.utillz.Resource
import kotlinx.coroutines.flow.Flow


interface ShoppingRepository{
    suspend fun createUser(createUser: Registration):Flow<Resource<AuthResponse>>
    suspend fun  getUser(userLogin: Login):Flow<Resource<AuthResponse>>
    suspend fun createAdmin(createAdmin: Registration):Flow<Resource<AuthResponse>>
    suspend fun loginAdmin(loginAdmin: Login):Flow<Resource<AuthResponse>>
    suspend fun getUserData(token: String): Flow<Resource<GetUserResponse>>

}

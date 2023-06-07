package com.example.tapshopping.domain


import com.example.tapshopping.data.model.*
import com.example.tapshopping.utillz.Resource
import kotlinx.coroutines.flow.Flow


interface ShoppingRepository{
    suspend fun createUser(createUser: Registration):Flow<Resource<AuthResponse>>
    suspend fun  getUser(userLogin: Login):Flow<Resource<AuthResponse>>
    suspend fun getUserData(token: String): Flow<Resource<GetUserResponse>>


}

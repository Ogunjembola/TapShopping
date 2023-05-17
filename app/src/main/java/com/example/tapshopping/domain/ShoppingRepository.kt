package com.example.tapshopping.domain


import com.example.tapshopping.data.model.*
import com.example.tapshopping.utillz.Resource
import kotlinx.coroutines.flow.Flow


interface ShoppingRepository{
    suspend fun createUser(createUser: UserRegistrationData):Flow<Resource<UsersResponse>>
    suspend fun  getUser(userLogin: UserLoginData):Flow<Resource<UsersResponse>>
    suspend fun createAdmin(createAdmin: CreateAdmin):Flow<Resource<AdminAuthResponse>>

}

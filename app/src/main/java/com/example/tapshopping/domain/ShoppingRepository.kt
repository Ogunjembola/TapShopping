package com.example.tapshopping.domain


import com.example.tapshopping.data.model.DataModel
import com.example.tapshopping.data.model.GetUserData
import com.example.tapshopping.data.model.UserLoginData
import com.example.tapshopping.data.model.UserRegistrationData
import com.example.tapshopping.data.model.UsersResponse
import com.example.tapshopping.data.model.AdminAuthResponse
import com.example.tapshopping.data.model.CreateAdmin
import com.example.tapshopping.utillz.Resource
import kotlinx.coroutines.flow.Flow


interface ShoppingRepository{
    suspend fun createUser(createUser: UserRegistrationData):Flow<Resource<UsersResponse>>
    suspend fun  getUser(userLogin: UserLoginData):Flow<Resource<UsersResponse>>

    suspend fun createAdmin(createAdmin: CreateAdmin):Flow<Resource<out AdminAuthResponse>>

}

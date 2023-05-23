package com.example.tapshopping.data.service

import com.example.tapshopping.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface NetworkService {

    @POST("admin")
    suspend fun createAdmin(@Body createAdmin: CreateAdmin): Response<AdminAuthResponse>

    @POST("user")
    suspend fun createUser(@Body userData: DataModel): Response<UsersResponse>

    @POST("user/login")
    suspend fun getRegisteredUsers(@Body userLogin: GetUserData): Response<UsersResponse>
}
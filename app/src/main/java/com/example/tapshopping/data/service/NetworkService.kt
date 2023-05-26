package com.example.tapshopping.data.service

import com.example.tapshopping.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface NetworkService {

    @POST("admin")
    suspend fun createAdmin(@Body createAdmin: Registration): Response<AuthResponse>

    @POST("admin/login")
    suspend fun adminLogin(@Body loginAdmin: Login): Response<AuthResponse>

    @POST("user")
    suspend fun createUser(@Body userData: Registration): Response<AuthResponse>

    @POST("user/login")
    suspend fun getRegisteredUsers(@Body userLogin: Login): Response<AuthResponse>

    @GET("user")
    suspend fun getUser( @Header("Authorization") token:String): Response<GetUserResponse>
}
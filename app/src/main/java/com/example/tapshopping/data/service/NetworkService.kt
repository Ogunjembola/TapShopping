package com.example.tapshopping.data.service

import com.example.tapshopping.data.model.AdminAuthResponse
import com.example.tapshopping.data.model.CreateAdmin
import com.example.tapshopping.utillz.Resource
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkService {

    @POST("admin")
    suspend fun createAdmin( @Body createAdmin: CreateAdmin):Response<AdminAuthResponse>
}
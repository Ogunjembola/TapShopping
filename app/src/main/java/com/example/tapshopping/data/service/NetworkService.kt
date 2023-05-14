package com.example.tapshopping.data.service

import com.example.tapshopping.data.model.DataModel
import com.example.tapshopping.data.model.GetUserData
import com.example.tapshopping.data.model.UsersResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkService {
    @POST("api/v1/user")
    suspend fun createUser(@Body userData: DataModel.Data)
            : Response<UsersResponse>

    @GET("api/v1/user")
    suspend fun getRegisteredUsers(userLogin: GetUserData.Data)
            :  Response<UsersResponse>
}
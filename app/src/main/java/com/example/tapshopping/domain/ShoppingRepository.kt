package com.example.tapshopping.domain



import com.example.tapshopping.data.model.DataModel
import com.example.tapshopping.data.model.GetUserData
import com.example.tapshopping.data.model.UsersResponse
import com.example.tapshopping.utillz.Resource
import kotlinx.coroutines.flow.Flow


interface ShoppingRepository{
    suspend fun createUser(createUser:DataModel.Data):Flow<Resource<UsersResponse>>

    suspend fun  getUser(userLogin:GetUserData.Data):Flow<Resource<UsersResponse>>

}


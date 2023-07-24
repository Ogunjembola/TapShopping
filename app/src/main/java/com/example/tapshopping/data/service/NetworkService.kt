package com.example.tapshopping.data.service

import com.example.tapshopping.data.model.*
import retrofit2.Response
import retrofit2.http.*


interface NetworkService {

    @DELETE("user/cart")
    suspend fun deleteCart(
        deleteCart: CartDelete,
        @Header("Authorization") token: String
    ): Response<CartDeleteResponse>

    @GET("user/cart")
    suspend fun getCart(@Header("Authorization") token: String): Response<CartResponse>


    @POST("user/cart")
    suspend fun createCart(
        @Body createCart: CreateCart,
        @Header("Authorization") token: String
    ): Response<CartResponse>

    @GET("product/6310c71798c307ca17e97ce2")
    suspend fun getAProducts(@Header("Authorization") token: String): Response<ProductResponse>

    @GET("product")
    suspend fun getProducts(@Header("Authorization") token: String): Response<ProductResponse>

    @POST("admin")
    suspend fun createAdmin(@Body createAdmin: Registration): Response<AuthResponse>

    @POST("admin/login")
    suspend fun adminLogin(@Body loginAdmin: Login): Response<AuthResponse>

    @POST("user")
    suspend fun createUser(@Body userData: Registration): Response<AuthResponse>

    @POST("user/login")
    suspend fun getRegisteredUsers(@Body userLogin: Login): Response<AuthResponse>

    @GET("user")
    suspend fun getUser(@Header("Authorization") token: String): Response<GetUserResponse>

    @GET("admin")
    suspend fun getAdmin(@Header("Authorization") token: String): Response<GetAdminResponse>

    @PUT("admin")
    suspend fun updateAdmin(
        @Header("Authorization") token: String,
        @Body updateUser: UpdateUser
    ): Response<AuthResponse>

    @POST("admin/categories/category")
    suspend fun createCategory(
        @Header("Authorization") token: String,
        @Body category: CreateCategory
    ): Response<AuthResponse>

    @GET("category")
    suspend fun getCategories(): Response<Category>

    @DELETE("admin/categories/category/{categoryId}")
    suspend fun deleteCategory(
        @Header("Authorization") token: String,
        @Path("categoryId") categoryId: String
    ): Response<AuthResponse>

    @PUT("admin/categories/category/{categoryId}")
    suspend fun updateCategory(
        @Header("Authorization") token: String,
        @Path("categoryId") categoryId: String,
        @Body category: CreateCategory
    ): Response<AuthResponse>

    @POST("admin/products/product")
    suspend fun createProduct(
        @Header("Authorization") token: String,
        @Body productData: CreateProduct
    ): Response<AuthResponse>
}
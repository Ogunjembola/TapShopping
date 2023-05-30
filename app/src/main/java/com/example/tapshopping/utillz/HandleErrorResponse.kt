package com.example.tapshopping.utillz

import com.example.tapshopping.data.model.AuthResponse
import com.google.gson.Gson

fun getAuthErrorResponse(body: String?): AuthResponse {
    return Gson().fromJson(body, AuthResponse::class.java)

}
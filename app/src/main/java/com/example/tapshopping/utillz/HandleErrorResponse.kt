package com.example.tapshopping.utillz

import com.example.tapshopping.data.model.AdminAuthResponse
import com.google.gson.Gson

inline fun <reified T>getAuthErrorResponse(body: String?): T {
    return Gson().fromJson(body, T::class.java)
}
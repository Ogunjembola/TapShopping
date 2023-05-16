package com.example.tapshopping.utillz

import com.example.tapshopping.data.model.AdminAuthResponse
import com.google.gson.Gson

 fun getAuthErrorResponse(body: String?): AdminAuthResponse {
    return Gson().fromJson(body, AdminAuthResponse::class.java)
}
package com.example.tapshopping.data.local

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    val isAdmin: Flow<Boolean>
    val isLoggedIn: Flow<Boolean>
    fun setIsLoggedIn(isLoggedIn:Boolean)
    suspend fun setIsAdmin(isAdmin:Boolean)
    var userName: String
    var email: String
    var fullName: String
    var token: String
    var userId: String
    var userType: String

    companion object{
        val ADMIN_LOGIN = booleanPreferencesKey("admin_login")
        val LOGIN_KEY = booleanPreferencesKey("login_key")
        const val USER_NAME = "username"
        const val EMAIL = "email"
        const val FULL_NAME = "full_name"
        const val TOKEN = "token"
        const val USER_ID = "userId"
        const val USER_TYPE = "user_type"
    }
}

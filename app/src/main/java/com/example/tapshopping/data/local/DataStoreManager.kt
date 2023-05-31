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

    companion object{
        val ADMIN_LOGIN = booleanPreferencesKey("admin_login")
        val LOGIN_KEY = booleanPreferencesKey("login_key")
        const val USER_NAME = "username"
        const val PhoneNumber = "phone_number"
        const val EMAIL = "email"
        const val FULL_NAME = "full_name"
        const val TOKEN = "token"
    }
}

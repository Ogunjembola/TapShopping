package com.example.tapshopping.core

import android.app.Application
import co.paystack.android.PaystackSdk
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class
ShopApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        //Initialize Paystack
        PaystackSdk.initialize(applicationContext)

    }
}
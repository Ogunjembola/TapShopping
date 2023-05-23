package com.example.tapshopping.core.di

import com.example.tapshopping.data.service.NetworkService
import com.example.tapshopping.utillz.API_CONNECT_TIMEOUT
import com.example.tapshopping.utillz.API_READ_TIMEOUT
import com.example.tapshopping.utillz.API_WRITE_TIMEOUT
import com.example.tapshopping.utillz.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideHttpLogger(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor().setLevel(level = HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLogger: HttpLoggingInterceptor): OkHttpClient{
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(API_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(API_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(API_READ_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(httpLogger)
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): NetworkService =
        retrofit.create(NetworkService::class.java)
}


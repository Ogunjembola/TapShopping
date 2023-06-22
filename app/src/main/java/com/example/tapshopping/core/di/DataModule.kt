package com.example.tapshopping.core.di

import com.example.tapshopping.data.local.DataStoreManager
import com.example.tapshopping.data.local.DataStoreManagerImpl
import com.example.tapshopping.domain.ShoppingAdminAuthRepository
import com.example.tapshopping.domain.ShoppingAdminAuthRepositoryImpl
import com.example.tapshopping.domain.ShoppingCategoryRepository
import com.example.tapshopping.domain.ShoppingCategoryRepositoryImpl
import com.example.tapshopping.domain.ShoppingRepository
import com.example.tapshopping.domain.ShoppingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindShoppingRepository(impl: ShoppingRepositoryImpl): ShoppingRepository

    @Binds
    @Singleton
    abstract fun bindDataStoreManager(dataStoreManagerImpl: DataStoreManagerImpl): DataStoreManager

    @Binds
    @Singleton
    abstract fun bindShoppingAdminAuthRepository(shoppingAdminAuthRepositoryImpl: ShoppingAdminAuthRepositoryImpl): ShoppingAdminAuthRepository

    @Binds
    @Singleton
    abstract fun bindShoppingCategoryRepository(shoppingCategoryRepositoryImpl: ShoppingCategoryRepositoryImpl): ShoppingCategoryRepository
}




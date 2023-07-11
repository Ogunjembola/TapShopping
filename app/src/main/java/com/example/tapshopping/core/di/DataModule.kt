package com.example.tapshopping.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.tapshopping.data.local.DataStoreManager
import com.example.tapshopping.data.local.DataStoreManagerImpl
import com.example.tapshopping.domain.CartRepository
import com.example.tapshopping.domain.CartRepostoryImpl
import com.example.tapshopping.domain.ProductRepository
import com.example.tapshopping.domain.ProductRepositoryImpl
import com.example.tapshopping.domain.ShoppingAdminAuthRepository
import com.example.tapshopping.domain.ShoppingAdminAuthRepositoryImpl
import com.example.tapshopping.domain.ShoppingCategoryRepository
import com.example.tapshopping.domain.ShoppingCategoryRepositoryImpl
import com.example.tapshopping.domain.ShoppingRepository
import com.example.tapshopping.domain.ShoppingRepositoryImpl
import com.example.tapshopping.utillz.SHOPPING_DATA_STORE_NAME
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
    abstract fun bindCategoryRepository(impl: ShoppingCategoryRepositoryImpl): ShoppingCategoryRepository
    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository
    @Binds
    @Singleton
    abstract fun bindCarRepository(impl: CartRepostoryImpl): CartRepository
}




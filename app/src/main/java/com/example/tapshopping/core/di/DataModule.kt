package com.example.tapshopping.core.di

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
    abstract fun bindInitiateShoppingRepository(impl: ShoppingRepositoryImpl): ShoppingRepository

//    @Provides
//    @Singleton
//    fun providesDataStorePreference(@ApplicationContext context: Context): DataStore<Preferences>{
//        return context.createDataStore(name = SHOPPING_DATA_STORE_NAME)
//    }

}
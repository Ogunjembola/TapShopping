package com.example.tapshopping.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.example.tapshopping.domain.ShoppingRepository
import com.example.tapshopping.domain.ShoppingRepositoryImpl
import com.example.tapshopping.utillz.SHOPPING_DATA_STORE_NAME
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
    abstract fun bindInitiateShoppingRepository(impl: ShoppingRepositoryImpl): ShoppingRepository

//    @Provides
//    @Singleton
//    fun providesDataStorePreference(@ApplicationContext context: Context): DataStore<Preferences>{
//        return context.createDataStore(name = SHOPPING_DATA_STORE_NAME)
//    }

}

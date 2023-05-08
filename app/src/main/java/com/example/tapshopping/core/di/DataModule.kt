package com.example.tapshopping.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.example.tapshopping.utillz.SHOPPING_DATA_STORE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.prefs.Preferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

//    @Provides
//    @Singleton
//    fun providesDataStorePreference(@ApplicationContext context: Context): DataStore<Preferences>{
//        return context.createDataStore(name = SHOPPING_DATA_STORE_NAME)
//    }

}
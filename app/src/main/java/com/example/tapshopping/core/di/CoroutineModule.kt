package com.example.tapshopping.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

class CoroutineModule {

    @Qualifier
    annotation class IoDispatcher

    @Qualifier
    annotation class DefaultDispatcher

    @Qualifier
    annotation class MainDispatcher

    @InstallIn(SingletonComponent::class)
    @Module
    object CoroutinesModule {

        @DefaultDispatcher
        @Provides
        fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

        @IoDispatcher
        @Provides
        fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

        @MainDispatcher
        @Provides
        fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
    }
}
package ru.kpfu.itis.core_data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Provides
    @IoDispatcher
    fun provideIoDispatcher() = Dispatchers.IO

    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher() = Dispatchers.Default

    @Provides
    @MainThreadDispatcher
    fun provideMainDispatcher() = Dispatchers.Main
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainThreadDispatcher


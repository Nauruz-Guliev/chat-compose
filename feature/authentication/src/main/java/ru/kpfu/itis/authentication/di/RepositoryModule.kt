package ru.kpfu.itis.authentication.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.authentication.data.repository.AuthRepositoryImpl
import ru.kpfu.itis.authentication.domain.repository.AuthRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindRepository(authRepository: AuthRepositoryImpl): AuthRepository
}
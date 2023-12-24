package ru.kpfu.itis.chat.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.chat.data.UserRepositoryImpl
import ru.kpfu.itis.chat.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository
}
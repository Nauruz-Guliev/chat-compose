package ru.kpfu.itis.profile.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.profile.data.repository.ProfileRepositoryImpl
import ru.kpfu.itis.profile.domain.repository.ProfileRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindProfileRepository(repositoryImpl: ProfileRepositoryImpl): ProfileRepository
}

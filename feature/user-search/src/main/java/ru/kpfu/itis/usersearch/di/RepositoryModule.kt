package ru.kpfu.itis.usersearch.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.usersearch.data.repository.UserSearchRepositoryImpl
import ru.kpfu.itis.usersearch.domain.repository.UserSearchRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindUserSearchRepository(userSearchRepository: UserSearchRepositoryImpl): UserSearchRepository
}

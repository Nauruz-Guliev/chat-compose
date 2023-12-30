package ru.kpfu.itis.user_search.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.user_search.data.repository.UserSearchRepositoryImpl
import ru.kpfu.itis.user_search.domain.repository.UserSearchRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindUserSearchRepository(userSearchRepository: UserSearchRepositoryImpl): UserSearchRepository
}
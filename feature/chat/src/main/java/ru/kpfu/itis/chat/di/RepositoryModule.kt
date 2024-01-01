package ru.kpfu.itis.chat.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.chat.data.repository.ChatListRepositoryImpl
import ru.kpfu.itis.chat.data.repository.ChatRepositoryImpl
import ru.kpfu.itis.chat.domain.repository.ChatListRepository
import ru.kpfu.itis.chat.domain.repository.ChatRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindChatListRepository(chatListRepositoryImpl: ChatListRepositoryImpl): ChatListRepository

    @Binds
    fun bindChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository
}
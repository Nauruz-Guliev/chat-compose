package ru.kpfu.itis.chat.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.chat.data.repository.ChatListRepositoryImpl
import ru.kpfu.itis.chat.data.repository.ChatRepositoryImpl
import ru.kpfu.itis.chat_api.ClearChatListAction
import ru.kpfu.itis.chat_api.ClearChatMessagesAction

@Module
@InstallIn(SingletonComponent::class)
interface DatabaseActionModule {

    @Binds
    fun bindClearChatListAction(chatListRepositoryImpl: ChatListRepositoryImpl): ClearChatListAction

    @Binds
    fun bindClearChatMessagesAction(chatRepositoryImpl: ChatRepositoryImpl): ClearChatMessagesAction
}
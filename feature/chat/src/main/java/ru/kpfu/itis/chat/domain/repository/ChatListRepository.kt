package ru.kpfu.itis.chat.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.chat.domain.model.ChatListModel
import ru.kpfu.itis.core_data.ChatUser

interface ChatListRepository {

    fun loadChatList(): Flow<List<ChatListModel>>
    fun getChatUser(userId: String?): ChatUser?
}
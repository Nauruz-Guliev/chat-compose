package ru.kpfu.itis.chat.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.chat.domain.model.ChatListModel

interface ChatRepository {
    fun loadChatList(): Flow<List<ChatListModel>>
}
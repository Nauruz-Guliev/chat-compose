package ru.kpfu.itis.chat.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.chat.domain.model.ChatMessageModel

interface ChatRepository {

    fun loadChat(chatId: String): Flow<List<ChatMessageModel>>
    suspend fun sendMessage(chatId: String, message: ChatMessageModel)
    fun getCurrentUserId(): String
}
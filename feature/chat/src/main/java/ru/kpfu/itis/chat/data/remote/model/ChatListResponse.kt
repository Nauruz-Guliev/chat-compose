package ru.kpfu.itis.chat.data.remote.model

import ru.kpfu.itis.core_data.ChatUser

data class ChatListResponse(
    val friend: ChatUser? = null,
    val chatId: String? = null,
    val lastUpdated: Long = 0
)
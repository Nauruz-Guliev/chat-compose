package ru.kpfu.itis.chat.domain.model

import ru.kpfu.itis.core_data.ChatUser

data class ChatListModel(
    val friend: ChatUser? = null,
    val chatId: String? = null,
)
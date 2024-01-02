package ru.kpfu.itis.chat.domain.model

data class ChatListModel(
    val user: ChatUserModel,
    val chatId: String?,
    val lastUpdated: Long
)
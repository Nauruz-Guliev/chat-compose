package ru.kpfu.itis.chat.domain.model

data class ChatListModel(
    val friend: ChatFriendModel,
    val chatId: String?,
    val lastUpdated: Long
)
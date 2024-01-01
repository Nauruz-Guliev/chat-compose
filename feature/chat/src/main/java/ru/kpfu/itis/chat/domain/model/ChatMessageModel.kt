package ru.kpfu.itis.chat.domain.model

data class ChatMessageModel(
    val sender: ChatFriendModel? = null,
    val message: String? = null,
    val time: Long? = null
)

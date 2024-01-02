package ru.kpfu.itis.chat.domain.model

data class ChatMessageModel(
    val sender: ChatUserModel? = null,
    val message: String? = null,
    val time: Long = -1
)

package ru.kpfu.itis.chat.data.remote.model


data class ChatMessageDto(
    val senderId: String? = null,
    val message: String? = null,
    val time: Long? = null
)
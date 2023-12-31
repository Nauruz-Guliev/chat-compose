package ru.kpfu.itis.chat_api

data class ChatMessage(
    val senderId: String,
    val message: String,
    val time: Long
)

package ru.kpfu.itis.chat_api

data class ChatRoom(
    val timeCreated: Long,
    val messages: List<ChatMessage>
)

val emptyChatRoom = ChatRoom(
    System.currentTimeMillis(),
    emptyList()
)
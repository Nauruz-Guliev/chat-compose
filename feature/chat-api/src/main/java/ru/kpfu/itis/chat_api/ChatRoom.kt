package ru.kpfu.itis.chat_api

data class ChatRoom(
    val timeCreated: Long = System.currentTimeMillis(),
)

val emptyChatRoom = ChatRoom()
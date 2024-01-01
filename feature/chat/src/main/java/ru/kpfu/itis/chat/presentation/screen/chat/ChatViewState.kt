package ru.kpfu.itis.chat.presentation.screen.chat

data class ChatViewState(
    val currentUserId: String = "",
    val chatId: String = "",
    val messages: List<ChatMessage> = emptyList()
)

package ru.kpfu.itis.chat.presentation.screen.chat

import ru.kpfu.itis.chat.domain.model.ChatUserModel

data class ChatState(
    val currentUserId: String = "",
    val chatId: String = "",
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val sender: ChatUserModel? = null
)

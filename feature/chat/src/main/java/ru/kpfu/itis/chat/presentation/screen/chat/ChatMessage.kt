package ru.kpfu.itis.chat.presentation.screen.chat

import ru.kpfu.itis.chat.domain.model.ChatUserModel

data class ChatMessage(
    val sender: ChatUserModel? = null,
    val message: String? = null,
    val time: String,
    val isMyMessage: Boolean
)
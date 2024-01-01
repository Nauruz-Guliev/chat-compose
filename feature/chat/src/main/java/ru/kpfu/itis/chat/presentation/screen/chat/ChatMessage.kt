package ru.kpfu.itis.chat.presentation.screen.chat

import ru.kpfu.itis.chat.domain.model.ChatFriendModel

data class ChatMessage(
    val sender: ChatFriendModel? = null,
    val message: String? = null,
    val time: String? = null,
    val isMyMessage: Boolean
)
package ru.kpfu.itis.chat.presentation.screen.chatlist

import ru.kpfu.itis.chat.domain.model.ChatUserModel

data class ChatItem(
    val friend: ChatUserModel,
    val chatId: String?,
    val lastUpdated: String
)

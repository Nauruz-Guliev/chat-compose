package ru.kpfu.itis.chat.presentation.screen.chat_list

import ru.kpfu.itis.chat.domain.model.ChatUserModel

data class ChatItem(
    val friend: ChatUserModel,
    val chatId: String?,
    val lastUpdated: String
)
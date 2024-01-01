package ru.kpfu.itis.chat.presentation.screen.chat_list

import ru.kpfu.itis.chat.domain.model.ChatFriendModel

data class ChatItem(
    val friend: ChatFriendModel,
    val chatId: String?,
    val lastUpdated: String
)
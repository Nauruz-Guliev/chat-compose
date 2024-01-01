package ru.kpfu.itis.chat.presentation.screen.chat_list

import ru.kpfu.itis.chat.domain.model.ChatListModel

data class ChatListViewState(
    val chatList: List<ChatListModel> = emptyList()
)

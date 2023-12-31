package ru.kpfu.itis.chat.presentation.mapper

import ru.kpfu.itis.chat.domain.model.ChatListModel
import ru.kpfu.itis.chat.presentation.screen.chatlist.ChatItem
import ru.kpfu.itis.coreui.extension.convertLongToTime

fun ChatListModel.mapToItem(): ChatItem {
    return ChatItem(
        friend = this.user,
        chatId = this.chatId,
        lastUpdated = this.lastUpdated.convertLongToTime()
    )
}

fun List<ChatListModel>.mapToItem(): List<ChatItem> {
    return this.map(ChatListModel::mapToItem)
}

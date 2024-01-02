package ru.kpfu.itis.chat.data.local.mapper

import ru.kpfu.itis.chat.data.local.entity.ChatRoom
import ru.kpfu.itis.chat.domain.model.ChatListModel

fun ChatRoom.mapToModel(): ChatListModel {
    return ChatListModel(
        friend = this.user.mapToModel(),
        chatId = this.chatListEntity.chatId,
        lastUpdated = this.chatListEntity.lastUpdated
    )
}

fun List<ChatRoom>.mapToModel(): List<ChatListModel> {
    return this.map(ChatRoom::mapToModel)
}
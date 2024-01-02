package ru.kpfu.itis.chat.data.remote.mapper

import ru.kpfu.itis.chat.data.remote.model.ChatListResponse
import ru.kpfu.itis.chat.domain.model.ChatFriendModel
import ru.kpfu.itis.chat.domain.model.ChatListModel

fun ChatListResponse.mapToModel(): ChatListModel {
    return ChatListModel(
        friend = ChatFriendModel(
            name = this.friend?.name,
            profileImage = this.friend?.profileImage
        ),
        chatId = this.chatId,
        lastUpdated = this.lastUpdated
    )
}

fun List<ChatListResponse>.mapToModel(): List<ChatListModel> =
    this.map(ChatListResponse::mapToModel)

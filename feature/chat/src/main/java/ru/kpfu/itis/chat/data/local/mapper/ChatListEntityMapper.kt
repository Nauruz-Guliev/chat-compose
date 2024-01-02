package ru.kpfu.itis.chat.data.local.mapper

import ru.kpfu.itis.chat.data.local.entity.ChatListEntity
import ru.kpfu.itis.chat.data.local.entity.ChatUserEntity
import ru.kpfu.itis.chat.domain.model.ChatListModel
import ru.kpfu.itis.core_data.ChatUser

fun ChatListModel.mapToChatEntity(): ChatListEntity {
    return ChatListEntity(
        chatId = chatId!!,
        senderId = this.friend.id,
        lastUpdated = this.lastUpdated
    )
}

fun List<ChatListModel>.mapToChatEntity(): List<ChatListEntity> {
    return this.map(ChatListModel::mapToChatEntity)
}

fun ChatListModel.mapToUserEntity(): ChatUserEntity {
    return with(this.friend) {
        ChatUserEntity(
            userId = id!!,
            name = name,
            profileImage = profileImage,
        )
    }
}

fun ChatUser.mapToEntity(): ChatUserEntity {
    return ChatUserEntity(
        userId = this.id!!,
        name = this.name,
        profileImage = this.profileImage
    )
}
package ru.kpfu.itis.chat.data.local.mapper

import ru.kpfu.itis.chat.data.local.entity.ChatUserEntity
import ru.kpfu.itis.chat.domain.model.ChatFriendModel

fun ChatUserEntity.mapToModel(): ChatFriendModel {
    return ChatFriendModel(
        id = userId,
        name = name,
        profileImage = profileImage
    )
}

fun ChatFriendModel.mapToEntity(): ChatUserEntity {
    return ChatUserEntity(
        userId = id!!,
        name = name,
        profileImage = profileImage
    )
}
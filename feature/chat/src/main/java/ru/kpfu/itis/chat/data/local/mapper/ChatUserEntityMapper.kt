package ru.kpfu.itis.chat.data.local.mapper

import ru.kpfu.itis.chat.data.local.entity.ChatUserEntity
import ru.kpfu.itis.chat.domain.model.ChatUserModel

fun ChatUserEntity.mapToModel(): ChatUserModel {
    return ChatUserModel(
        id = userId,
        name = name,
        profileImage = profileImage
    )
}
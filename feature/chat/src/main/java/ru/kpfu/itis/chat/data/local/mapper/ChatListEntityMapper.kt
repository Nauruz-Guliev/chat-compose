package ru.kpfu.itis.chat.data.local.mapper

import ru.kpfu.itis.chat.data.local.entity.ChatUserEntity
import ru.kpfu.itis.core_data.ChatUser


fun ChatUser.mapToEntity(): ChatUserEntity {
    return ChatUserEntity(
        userId = this.id!!,
        name = this.name,
        profileImage = this.profileImage
    )
}
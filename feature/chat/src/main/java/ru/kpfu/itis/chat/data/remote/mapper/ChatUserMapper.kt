package ru.kpfu.itis.chat.data.remote.mapper

import ru.kpfu.itis.chat.domain.model.ChatUserModel
import ru.kpfu.itis.core_data.ChatUser

fun ChatUser.mapToModel(): ChatUserModel {
    return ChatUserModel(
        id = id,
        name = name,
        profileImage = this.profileImage
    )
}
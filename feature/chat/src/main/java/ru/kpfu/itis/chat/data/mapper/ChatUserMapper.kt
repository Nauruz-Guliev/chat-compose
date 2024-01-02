package ru.kpfu.itis.chat.data.mapper

import ru.kpfu.itis.chat.domain.model.ChatFriendModel
import ru.kpfu.itis.core_data.ChatUser

fun ChatUser.mapToModel(): ChatFriendModel {
    return ChatFriendModel(
        id = id,
        name = name,
        profileImage = this.profileImage
    )
}
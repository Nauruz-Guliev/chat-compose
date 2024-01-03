package ru.kpfu.itis.user_search.data.mapper

import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.user_search.domain.model.User

fun ChatUser.mapToUser(): User {
    return User(
        id = id,
        name = name,
        profileImage = profileImage
    )
}
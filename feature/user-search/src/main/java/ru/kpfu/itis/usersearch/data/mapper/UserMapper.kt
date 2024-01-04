package ru.kpfu.itis.usersearch.data.mapper

import ru.kpfu.itis.coredata.ChatUser
import ru.kpfu.itis.usersearch.domain.model.User

fun ChatUser.mapToUser(): User {
    return User(
        id = id,
        name = name,
        profileImage = profileImage
    )
}

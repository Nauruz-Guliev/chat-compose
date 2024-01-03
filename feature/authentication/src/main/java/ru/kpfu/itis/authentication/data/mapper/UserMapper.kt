package ru.kpfu.itis.authentication.data.mapper

import com.google.firebase.auth.FirebaseUser
import ru.kpfu.itis.authentication.domain.model.User
import ru.kpfu.itis.core_data.ChatUser

fun User.toChatUser(id: String): ChatUser {
    return ChatUser(
        name = name,
        email = email,
        id = id
    )
}

fun FirebaseUser.mapToLocalUser(): User {
    return User(
        name = this.displayName,
        email = this.email,
    )
}
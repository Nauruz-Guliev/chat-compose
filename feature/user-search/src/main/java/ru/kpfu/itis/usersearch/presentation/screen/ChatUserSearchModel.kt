package ru.kpfu.itis.usersearch.presentation.screen

import ru.kpfu.itis.usersearch.domain.model.User

data class ChatUserSearchModel(
    val user: User,
    val isProfileImageValid: Boolean,
    val isInFriendList: Boolean
)

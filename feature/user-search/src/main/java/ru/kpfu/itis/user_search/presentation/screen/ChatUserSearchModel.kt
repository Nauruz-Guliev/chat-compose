package ru.kpfu.itis.user_search.presentation.screen

import ru.kpfu.itis.user_search.domain.model.User

data class ChatUserSearchModel(
    val user: User,
    val isProfileImageValid: Boolean,
    val isInFriendList: Boolean
)
package ru.kpfu.itis.user_search.presentation.screen

import ru.kpfu.itis.core_data.ChatUser

data class ChatUserSearchModel(
    val user: ChatUser,
    val isProfileImageValid: Boolean
)
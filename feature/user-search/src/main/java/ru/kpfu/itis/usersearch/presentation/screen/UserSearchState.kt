package ru.kpfu.itis.usersearch.presentation.screen

data class UserSearchState(
    val users: List<ChatUserSearchModel> = emptyList(),
    val existingChats: List<String> = emptyList()
)

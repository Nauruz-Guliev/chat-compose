package ru.kpfu.itis.user_search.presentation.screen


data class UserSearchState(
    val users: List<ChatUserSearchModel> = emptyList()
)

package ru.kpfu.itis.user_search.presentation.screen

sealed interface UserSearchSideEffect {

    data class ExceptionHappened(val throwable: Throwable) : UserSearchSideEffect
}
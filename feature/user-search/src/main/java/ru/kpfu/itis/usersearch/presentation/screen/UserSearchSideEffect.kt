package ru.kpfu.itis.usersearch.presentation.screen

sealed interface UserSearchSideEffect {

    data class ExceptionHappened(val throwable: Throwable) : UserSearchSideEffect
}

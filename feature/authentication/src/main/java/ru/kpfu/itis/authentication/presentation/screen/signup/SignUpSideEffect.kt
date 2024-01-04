package ru.kpfu.itis.authentication.presentation.screen.signup

sealed interface SignUpSideEffect {

    data class ExceptionHappened(val throwable: Throwable) : SignUpSideEffect
}

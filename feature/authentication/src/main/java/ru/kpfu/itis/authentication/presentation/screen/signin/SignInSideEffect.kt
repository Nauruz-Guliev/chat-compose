package ru.kpfu.itis.authentication.presentation.screen.signin

sealed interface SignInSideEffect {

    data class ExceptionHappened(val throwable: Throwable) : SignInSideEffect
}

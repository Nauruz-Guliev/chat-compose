package ru.kpfu.itis.authentication.presentation.screen.signup

sealed interface SignUpSideEffect {

    data class ExceptionHappened(val throwable: Throwable?) : SignUpSideEffect
    data object ShowLoading : SignUpSideEffect
    data object ValidationFailure: SignUpSideEffect
}
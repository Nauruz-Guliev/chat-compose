package ru.kpfu.itis.authentication.presentation.screen.signup

sealed interface SignUpSideEffect {

    data class ExceptionHappened(val throwable: Throwable?) : SignUpSideEffect
    data object ShowLoading : SignUpSideEffect
    data object NavigateBack : SignUpSideEffect
    data object NavigateToLogin : SignUpSideEffect
    data object NavigateToMainFragment : SignUpSideEffect
}
package ru.kpfu.itis.profile.presentation.screen

sealed interface ProfileSideEffect {

    data class ExceptionHappened(val throwable: Throwable?) : ProfileSideEffect
    data object ShowLoading : ProfileSideEffect
    data object ValidationFailure: ProfileSideEffect
}

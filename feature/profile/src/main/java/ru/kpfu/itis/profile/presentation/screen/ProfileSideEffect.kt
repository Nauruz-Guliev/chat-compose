package ru.kpfu.itis.profile.presentation.screen

import ru.kpfu.itis.core_data.ChatUser

sealed interface ProfileSideEffect {

    data class ExceptionHappened(val throwable: Throwable?) : ProfileSideEffect
    data class UserLoaded(val user: ChatUser?): ProfileSideEffect
}

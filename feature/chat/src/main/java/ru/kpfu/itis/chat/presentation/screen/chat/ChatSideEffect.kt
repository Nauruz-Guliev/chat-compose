package ru.kpfu.itis.chat.presentation.screen.chat

sealed interface ChatSideEffect {

    data class ExceptionHappened(val throwable: Throwable) : ChatSideEffect
}

package ru.kpfu.itis.chat.presentation.screen.chatlist

sealed interface ChatListSideEffect {

    data class ExceptionHappened(val throwable: Throwable) : ChatListSideEffect
}

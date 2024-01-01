package ru.kpfu.itis.chat.presentation.screen.chat_list

sealed interface ChatListSideEffect {

    data class ExceptionHappened(val throwable: Throwable) : ChatListSideEffect
}

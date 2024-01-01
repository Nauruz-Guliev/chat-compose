package ru.kpfu.itis.chat_api

enum class ChatDestinations(val route: String) {
    CHAT_SCREEN("CHAT_SCREEN/{$CHAT_ID_KEY}"),
    CHAT_LIST_SCREEN("CHAT_LIST_SCREEN")
}

const val CHAT_ID_KEY = "chatId"
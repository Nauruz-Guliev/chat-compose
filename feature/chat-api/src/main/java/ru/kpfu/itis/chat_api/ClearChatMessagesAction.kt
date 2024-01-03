package ru.kpfu.itis.chat_api

interface ClearChatMessagesAction {

    suspend fun clearChatMessages()
}
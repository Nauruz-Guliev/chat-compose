package ru.kpfu.itis.chat.domain.usecase

import ru.kpfu.itis.chat.domain.model.ChatMessageModel
import ru.kpfu.itis.chat.domain.repository.ChatRepository
import javax.inject.Inject

class SendMessage @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(chatId: String, message: ChatMessageModel) {
        repository.sendMessage(chatId, message)
    }
}
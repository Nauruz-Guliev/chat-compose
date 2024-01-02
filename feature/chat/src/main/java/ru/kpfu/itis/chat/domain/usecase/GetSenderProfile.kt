package ru.kpfu.itis.chat.domain.usecase

import ru.kpfu.itis.chat.domain.model.ChatUserModel
import ru.kpfu.itis.chat.domain.repository.ChatRepository
import javax.inject.Inject

class GetSenderProfile @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String): ChatUserModel? {
        return chatRepository.getSenderProfile(chatId)
    }
}
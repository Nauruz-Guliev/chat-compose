package ru.kpfu.itis.chat.domain.usecase

import ru.kpfu.itis.chat.domain.model.ChatFriendModel
import ru.kpfu.itis.chat.domain.repository.ChatRepository
import javax.inject.Inject

class GetSenderProfile @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String): ChatFriendModel? {
        return chatRepository.getSenderProfile(chatId)
    }
}
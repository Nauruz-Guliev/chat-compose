package ru.kpfu.itis.chat.domain.usecase

import ru.kpfu.itis.chat.domain.repository.ChatRepository
import javax.inject.Inject

class GetCurrentUserId @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(): String {
        return chatRepository.getCurrentUserId()
    }
}
package ru.kpfu.itis.chat.domain.usecase

import ru.kpfu.itis.chat.domain.repository.ChatListRepository
import javax.inject.Inject

class ClearChatListHistory @Inject constructor(
    private val repository: ChatListRepository
) {
    suspend operator fun invoke() {
        repository.clearChatListValues()
    }
}
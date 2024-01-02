package ru.kpfu.itis.chat.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.chat.domain.model.ChatMessageModel
import ru.kpfu.itis.chat.domain.repository.ChatRepository
import javax.inject.Inject

class GetMessages @Inject constructor(
    private val repository: ChatRepository
) {

    operator fun invoke(chatId: String): Flow<List<ChatMessageModel>> {
        return repository.loadChat(chatId)
    }
}
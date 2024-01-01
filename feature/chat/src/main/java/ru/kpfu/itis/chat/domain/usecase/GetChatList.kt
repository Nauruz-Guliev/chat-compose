package ru.kpfu.itis.chat.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.chat.domain.model.ChatListModel
import ru.kpfu.itis.chat.domain.repository.ChatRepository
import javax.inject.Inject

class GetChatList @Inject constructor(
    private val repository: ChatRepository
) {
    operator fun invoke(): Flow<List<ChatListModel>> {
        return repository.loadChatList()
    }
}
package ru.kpfu.itis.chat.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.chat.domain.model.ChatListModel
import ru.kpfu.itis.chat.domain.repository.ChatListRepository
import javax.inject.Inject

class GetChatList @Inject constructor(
    private val repository: ChatListRepository
) {
    operator fun invoke(): Flow<List<ChatListModel>> {
        return repository.loadChatList()
    }
}

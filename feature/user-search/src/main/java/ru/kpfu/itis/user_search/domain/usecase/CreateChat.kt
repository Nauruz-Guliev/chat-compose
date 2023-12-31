package ru.kpfu.itis.user_search.domain.usecase

import ru.kpfu.itis.user_search.domain.repository.UserSearchRepository
import javax.inject.Inject

class CreateChat @Inject constructor(
    private val repository: UserSearchRepository
) {
    suspend operator fun invoke(friendId: String) {
        repository.startChatting(friendId)
    }
}
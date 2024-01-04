package ru.kpfu.itis.usersearch.domain.usecase

import ru.kpfu.itis.usersearch.domain.repository.UserSearchRepository
import javax.inject.Inject

class CreateChat @Inject constructor(
    private val repository: UserSearchRepository
) {
    suspend operator fun invoke(friendId: String) {
        repository.createChat(friendId)
    }
}

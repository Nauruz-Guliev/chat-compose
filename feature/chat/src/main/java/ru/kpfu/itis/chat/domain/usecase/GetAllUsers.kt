package ru.kpfu.itis.chat.domain.usecase

import ru.kpfu.itis.chat.domain.repository.UserRepository
import ru.kpfu.itis.core_data.ChatUser
import javax.inject.Inject

class GetAllUsers @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() : List<ChatUser>{
        return repository.getAllUsers().map {
            ChatUser()
        }
    }
}
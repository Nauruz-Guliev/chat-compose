package ru.kpfu.itis.profile.domain.usecase

import ru.kpfu.itis.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class ClearCachedData @Inject constructor(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke() {
        repository.run {
            clearCachedChatList()
            clearCachedChatMessages()
        }
    }
}

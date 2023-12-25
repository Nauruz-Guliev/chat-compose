package ru.kpfu.itis.profile.domain.usecase

import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class GetChatUser @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(): ChatUser? {
        return profileRepository.getUser()
    }
}

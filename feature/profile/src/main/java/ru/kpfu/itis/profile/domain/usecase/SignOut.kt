package ru.kpfu.itis.profile.domain.usecase

import ru.kpfu.itis.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class SignOut @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke() {
        return profileRepository.signOut()
    }
}

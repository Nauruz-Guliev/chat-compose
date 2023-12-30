package ru.kpfu.itis.profile.domain.usecase

import ru.kpfu.itis.profile.domain.model.UpdateProfileModel
import ru.kpfu.itis.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateUser @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(model: UpdateProfileModel) {
        return profileRepository.updateProfile(model)
    }
}

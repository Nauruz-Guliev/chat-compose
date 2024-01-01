package ru.kpfu.itis.profile.domain.repository

import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.profile.domain.model.UpdateProfileModel

interface ProfileRepository {

    suspend fun updateProfile(model: UpdateProfileModel)
    suspend fun getUser(): ChatUser?
    suspend fun clearUserId()
    suspend fun signOut()
}
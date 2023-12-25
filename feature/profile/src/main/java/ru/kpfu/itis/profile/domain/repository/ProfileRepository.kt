package ru.kpfu.itis.profile.domain.repository

import ru.kpfu.itis.core_data.ChatUser

interface ProfileRepository {

    suspend fun updateProfile(newEmail: String, name: String)
    suspend fun getUser(): ChatUser?
}
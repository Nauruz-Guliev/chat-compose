package ru.kpfu.itis.profile.domain.model

data class UpdateProfileModel(
    val name: String,
    val email: String,
    val profileImage: String?
)

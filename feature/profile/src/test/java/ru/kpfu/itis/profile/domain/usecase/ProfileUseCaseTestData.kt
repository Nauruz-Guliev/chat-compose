package ru.kpfu.itis.profile.domain.usecase

import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.profile.domain.model.UpdateProfileModel

const val EMAIL = "aleksei@test.com"
const val NAME = "Aleksei"
const val PASSWORD = "SECRET_PASSWORD"
const val PROFILE_IMAGE_URL = "image.com/aleksei_image"

val chatUser = ChatUser(
    name = NAME,
    profileImage = PROFILE_IMAGE_URL,
    email = EMAIL,
    friendsList = emptyList()
)

val updateProfileModel = UpdateProfileModel(
    name = NAME,
    email = EMAIL,
    password = PASSWORD,
    profileImage = PROFILE_IMAGE_URL
)

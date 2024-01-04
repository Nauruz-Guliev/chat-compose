package ru.kpfu.itis.profile.domain.usecase

import ru.kpfu.itis.coredata.ChatUser
import ru.kpfu.itis.profile.domain.model.UpdateProfileModel

const val EMAIL = "aleksei@test.com"
const val NAME = "Aleksei"
const val PASSWORD = "SECRET_PASSWORD"
const val PROFILE_IMAGE_URL = "image.com/aleksei_image"
const val USER_ID = "id"

val chatUser = ChatUser(
    name = NAME,
    profileImage = PROFILE_IMAGE_URL,
    email = EMAIL,
    id = USER_ID
)

val updateProfileModel = UpdateProfileModel(
    name = NAME,
    email = EMAIL,
    profileImage = PROFILE_IMAGE_URL,
)

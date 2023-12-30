package ru.kpfu.itis.profile.domain.usecase

import ru.kpfu.itis.core_data.ChatUser

const val EMAIL = "aleksei@test.com"
const val NAME = "Aleksei"

val chatUser = ChatUser(
    name = NAME,
    profileImage = "image.com/aleksei_image",
    email = EMAIL,
    friendsList = emptyList()
)

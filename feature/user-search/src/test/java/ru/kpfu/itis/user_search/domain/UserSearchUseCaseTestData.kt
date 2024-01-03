package ru.kpfu.itis.user_search.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.kpfu.itis.user_search.domain.model.User

const val USER_ID = "id"
const val USER_NAME = "name"

fun getExistingChats(): Flow<List<String>> {
    return flowOf(
        listOf(
            "id1", "id2", "id3"
        )
    )
}

fun getUsers(): Flow<List<User>> {
    return flowOf(
        listOf(
            User(
                id = USER_ID,
                name = USER_NAME,
                profileImage = "cool_image.com/hahahahaha"
            )
        )
    )
}
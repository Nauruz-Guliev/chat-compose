package ru.kpfu.itis.usersearch.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.usersearch.domain.model.User

interface UserSearchRepository {

    fun findUser(name: String): Flow<List<User>>
    suspend fun createChat(userId: String)
    fun loadExistingChats(): Flow<List<String>>
}

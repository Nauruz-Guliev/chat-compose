package ru.kpfu.itis.user_search.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.user_search.domain.model.User

interface UserSearchRepository {

    fun findUser(name: String): Flow<List<User>>
    suspend fun createChat(userId: String)
    fun loadExistingChats(): Flow<List<String>>
}
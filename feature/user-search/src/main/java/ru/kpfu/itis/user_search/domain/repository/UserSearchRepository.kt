package ru.kpfu.itis.user_search.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.core_data.ChatUser

interface UserSearchRepository {

    fun findUser(name: String): Flow<List<ChatUser>>
    suspend fun startChatting(userId: String)
    fun loadExistingChats(): Flow<List<String>>
}
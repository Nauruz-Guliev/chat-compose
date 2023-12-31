package ru.kpfu.itis.user_search.data.repository

import android.security.keystore.UserNotAuthenticatedException
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import ru.kpfu.itis.chat_api.ChatReference
import ru.kpfu.itis.chat_api.ChatRoom
import ru.kpfu.itis.chat_api.emptyChatRoom
import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.core_data.UserStore
import ru.kpfu.itis.core_data.di.ChatsDatabase
import ru.kpfu.itis.core_data.di.UsersDatabase
import ru.kpfu.itis.user_search.domain.repository.UserSearchRepository
import javax.inject.Inject

class UserSearchRepositoryImpl @Inject constructor(
    @UsersDatabase
    private val userDatabase: DatabaseReference,
    @ChatsDatabase
    private val chatDatabase: DatabaseReference,
    private val userStore: UserStore
) : UserSearchRepository {

    override fun findUser(name: String): Flow<List<ChatUser>> = flow {
        val userListTask = userDatabase.get().also {
            it.await()
        }
        val currentUserId = userStore.getUserId()
        userListTask.result.children
            .mapNotNull { dataSnapshot ->
                dataSnapshot.getValue(ChatUser::class.java)?.apply {
                    id = dataSnapshot.key
                }
            }
            .filter { it.id != currentUserId }
            .filter { it.name?.contains(name, ignoreCase = true) == true }
            .also { emit(it) }
    }

    override suspend fun startChatting(userId: String) {
        val currentUserId = userStore.getUserId() ?: throw UserNotAuthenticatedException()
        val chatId = currentUserId + userId
        createChatReferenceForEachUser(userId, currentUserId, chatId)
        createChatReferenceForEachUser(currentUserId, userId, chatId)
        createChatRoom(chatId)
    }

    private suspend fun createChatReferenceForEachUser(
        firstUserId: String,
        secondUserId: String,
        chatId: String
    ) {
        userDatabase.child(firstUserId)
            .child("chats")
            .child(chatId)
            .setValue(ChatReference(secondUserId))
            .await()
    }

    private suspend fun createChatRoom(chatId: String) {
        val chatRoom = chatDatabase.child(chatId)
            .get()
            .also { task -> task.await() }
            .result
            .getValue(ChatRoom::class.java)
        if (chatRoom == null) {
            chatDatabase.child(chatId)
                .setValue(emptyChatRoom)
                .await()
        }
    }
}

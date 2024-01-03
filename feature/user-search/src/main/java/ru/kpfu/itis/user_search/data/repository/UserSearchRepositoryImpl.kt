package ru.kpfu.itis.user_search.data.repository

import android.security.keystore.UserNotAuthenticatedException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import ru.kpfu.itis.chat_api.ChatReference
import ru.kpfu.itis.chat_api.ChatRoom
import ru.kpfu.itis.chat_api.emptyChatRoom
import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.core_data.addListenerAsFlow
import ru.kpfu.itis.core_data.di.ChatsDatabase
import ru.kpfu.itis.core_data.di.IoDispatcher
import ru.kpfu.itis.core_data.di.UsersDatabase
import ru.kpfu.itis.user_search.data.mapper.mapToUser
import ru.kpfu.itis.user_search.domain.model.User
import ru.kpfu.itis.user_search.domain.repository.UserSearchRepository
import javax.inject.Inject

private const val CHATS_PATH_KEY = "chats"
private const val PROFILE_PATH_KEY = "profile"

class UserSearchRepositoryImpl @Inject constructor(
    @UsersDatabase
    private val userDatabase: DatabaseReference,
    @ChatsDatabase
    private val chatDatabase: DatabaseReference,
    private val firebaseAuth: FirebaseAuth,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
) : UserSearchRepository {

    override fun findUser(name: String): Flow<List<User>> {
        return userDatabase.addListenerAsFlow()
            .map { mapFoundUsers(it, name) }
            .flowOn(dispatcher)
    }

    private fun mapFoundUsers(snapshot: DataSnapshot, name: String): List<User> {
        val currentUserId = getCurrentUserId()
        return snapshot.children
            .mapNotNull { dataSnapshot ->
                dataSnapshot.child(PROFILE_PATH_KEY)
                    .getValue(ChatUser::class.java)?.apply {
                        id = dataSnapshot.key ?: "UNKNOWN_ID"
                    }?.mapToUser()
            }
            .filter { it.id != currentUserId }
            .filter { it.name?.contains(name, ignoreCase = true) == true }
    }

    override suspend fun createChat(userId: String) {
        val currentUserId = getCurrentUserId()
        val chatId = currentUserId + userId
        createChatReferenceForEachUser(userId, currentUserId, chatId)
        createChatReferenceForEachUser(currentUserId, userId, chatId)
        createChatRoom(chatId)
    }

    private fun getCurrentUserId(): String {
        return firebaseAuth.currentUser?.uid ?: throw UserNotAuthenticatedException()
    }

    override fun loadExistingChats(): Flow<List<String>> {
        val currentUserId = getCurrentUserId()
        return userDatabase.child(currentUserId)
            .child(CHATS_PATH_KEY)
            .addListenerAsFlow()
            .map(::mapUserIds)
            .flowOn(dispatcher)
    }

    private fun mapUserIds(snapshot: DataSnapshot): List<String> {
        return snapshot.children.mapNotNull { result ->
            result.getValue(ChatReference::class.java)?.friendId
        }
    }

    private suspend fun createChatReferenceForEachUser(
        firstUserId: String,
        secondUserId: String,
        chatId: String
    ) {
        userDatabase.child(firstUserId)
            .child(CHATS_PATH_KEY)
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

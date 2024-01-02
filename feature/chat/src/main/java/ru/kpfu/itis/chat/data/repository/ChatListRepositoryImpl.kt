package ru.kpfu.itis.chat.data.repository

import android.security.keystore.UserNotAuthenticatedException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.kpfu.itis.chat.data.local.dao.ChatListDao
import ru.kpfu.itis.chat.data.local.entity.ChatListItemEntity
import ru.kpfu.itis.chat.data.local.entity.ChatRoom
import ru.kpfu.itis.chat.data.local.mapper.mapToEntity
import ru.kpfu.itis.chat.data.local.mapper.mapToModel
import ru.kpfu.itis.chat.domain.model.ChatListModel
import ru.kpfu.itis.chat.domain.repository.ChatListRepository
import ru.kpfu.itis.chat_api.ChatReference
import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.core_data.UserService
import ru.kpfu.itis.core_data.addListenerAsFlow
import ru.kpfu.itis.core_data.di.IoDispatcher
import ru.kpfu.itis.core_data.di.UsersDatabase
import javax.inject.Inject

class ChatListRepositoryImpl @Inject constructor(
    @UsersDatabase
    private val databaseReference: DatabaseReference,
    private val firebaseAuth: FirebaseAuth,
    private val userService: UserService,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val chatListDao: ChatListDao
) : ChatListRepository {

    override fun loadChatList(): Flow<List<ChatListModel>> {
        val remoteFlow = handleRemoteChanges()
        val localFlow = chatListDao.getCachedChatList()
            .map(List<ChatRoom>::mapToModel)
        return combine(localFlow, remoteFlow) { l, _ -> l }
            .flowOn(dispatcher)
    }

    private fun handleRemoteChanges(): Flow<Unit> {
        val currentUserId = firebaseAuth.currentUser?.uid ?: throw UserNotAuthenticatedException()
        return callbackFlow {
            databaseReference.child(currentUserId)
                .child("chats")
                .addListenerAsFlow(this)
        }.map { dataSnapshot ->
            saveRemoteChanges(dataSnapshot)
        }
    }

    private fun saveRemoteChanges(dataSnapshot: DataSnapshot) {
        dataSnapshot.children.forEach { result ->
            val chatReference = result.getValue(ChatReference::class.java)
            val chatId = result.key
            val user = getChatUser(chatReference?.friendId)?.mapToEntity()
            if (chatId != null && user != null) {
                chatListDao.run {
                    save(user)
                    save(
                        ChatListItemEntity(
                            chatId = chatId,
                            senderId = chatReference?.friendId,
                            lastUpdated = System.currentTimeMillis()
                        )
                    )
                }
            }
        }
    }

    override suspend fun clearChatListValues() {
        chatListDao.clearDatabase()
    }

    override fun getChatUser(userId: String?): ChatUser? {
        if (userId == null) return null
        return userService.getUserById(userId)
    }
}

package ru.kpfu.itis.chat.data.repository

import android.security.keystore.UserNotAuthenticatedException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import ru.kpfu.itis.chat.data.local.dao.ChatMessagesDao
import ru.kpfu.itis.chat.data.local.mapper.mapToEntity
import ru.kpfu.itis.chat.data.local.mapper.mapToModel
import ru.kpfu.itis.chat.data.remote.mapper.mapToDto
import ru.kpfu.itis.chat.data.remote.mapper.mapToModel
import ru.kpfu.itis.chat.data.remote.model.ChatMessageDto
import ru.kpfu.itis.chat.domain.model.ChatMessageModel
import ru.kpfu.itis.chat.domain.model.ChatUserModel
import ru.kpfu.itis.chat.domain.repository.ChatRepository
import ru.kpfu.itis.chat_api.ChatReference
import ru.kpfu.itis.chat_api.ClearChatMessagesAction
import ru.kpfu.itis.coredata.UserService
import ru.kpfu.itis.coredata.addListenerAsFlow
import ru.kpfu.itis.coredata.awaitTask
import ru.kpfu.itis.coredata.di.ChatsDatabase
import ru.kpfu.itis.coredata.di.IoDispatcher
import ru.kpfu.itis.coredata.di.UsersDatabase
import javax.inject.Inject

private const val MESSAGES_PATH = "messages"

class ChatRepositoryImpl @Inject constructor(
    @ChatsDatabase
    private val chatsDatabase: DatabaseReference,
    @UsersDatabase
    private val usersDatabase: DatabaseReference,
    private val firebaseAuth: FirebaseAuth,
    private val userService: UserService,
    private val chatDao: ChatMessagesDao,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
) : ChatRepository, ClearChatMessagesAction {

    override fun loadChat(chatId: String): Flow<List<ChatMessageModel>> {
        val localFlow = chatDao.getCachedMessages(chatId)
            .map { chat ->
                chat.messages.map { messageEntity ->
                    val senderId = messageEntity.sender?.userId
                    val sender = userService.getUserById(senderId)?.mapToModel()
                    messageEntity.mapToModel(sender)
                }.sortedBy { it.time }
            }
        val remoteFlow = handleRemoteChanges(chatId)
        return combine(localFlow, remoteFlow) { l, _ -> l }
            .flowOn(dispatcher)
    }

    private fun handleRemoteChanges(chatId: String): Flow<Unit> {
        return chatsDatabase.child(chatId)
            .child(MESSAGES_PATH)
            .addListenerAsFlow()
            .map { snapshot ->
                saveRemoteChanges(snapshot, chatId)
            }
    }

    private fun saveRemoteChanges(dataSnapshot: DataSnapshot, chatId: String) {
        dataSnapshot.children.mapNotNull { result ->
            val messageDto = result.getValue(ChatMessageDto::class.java)
            val user = userService.getUserById(messageDto?.senderId)?.mapToEntity()
            val messageEntity = messageDto?.mapToEntity(user, chatId)
            if (messageEntity != null) {
                chatDao.save(messageEntity)
            }
        }
    }

    override suspend fun sendMessage(chatId: String, message: ChatMessageModel): Unit =
        withContext(dispatcher) {
            chatsDatabase.child(chatId)
                .child(MESSAGES_PATH)
                .child(message.time.toString())
                .setValue(message.mapToDto())
                .await()
        }

    override fun getCurrentUserId(): String {
        return firebaseAuth.currentUser?.uid ?: throw UserNotAuthenticatedException()
    }

    override suspend fun getSenderProfile(chatId: String): ChatUserModel? =
        withContext(dispatcher) {
            val currentUserId = getCurrentUserId()
            val userTask = usersDatabase.child(currentUserId)
                .child("chats")
                .child(chatId)
                .get()
            awaitTask(userTask)
            val chatReference = userTask.result.getValue(ChatReference::class.java)
            userService.getUserById(chatReference?.friendId)?.mapToModel()
        }

    override suspend fun clearChatMessages() {
        chatDao.clearAllMessages()
    }
}

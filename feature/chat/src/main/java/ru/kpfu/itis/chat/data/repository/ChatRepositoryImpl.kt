package ru.kpfu.itis.chat.data.repository

import android.security.keystore.UserNotAuthenticatedException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import ru.kpfu.itis.chat.data.remote.mapper.mapToDto
import ru.kpfu.itis.chat.data.remote.mapper.mapToModel
import ru.kpfu.itis.chat.data.remote.model.ChatMessageDto
import ru.kpfu.itis.chat.domain.model.ChatFriendModel
import ru.kpfu.itis.chat.domain.model.ChatMessageModel
import ru.kpfu.itis.chat.domain.repository.ChatRepository
import ru.kpfu.itis.chat_api.ChatReference
import ru.kpfu.itis.core_data.UserService
import ru.kpfu.itis.core_data.addListenerAsFlow
import ru.kpfu.itis.core_data.awaitTask
import ru.kpfu.itis.core_data.di.ChatsDatabase
import ru.kpfu.itis.core_data.di.IoDispatcher
import ru.kpfu.itis.core_data.di.UsersDatabase
import javax.inject.Inject

private const val MESSAGES_PATH = "messages"

class ChatRepositoryImpl @Inject constructor(
    @ChatsDatabase
    private val chatsDatabase: DatabaseReference,
    @UsersDatabase
    private val usersDatabase: DatabaseReference,
    private val firebaseAuth: FirebaseAuth,
    private val userService: UserService,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
) : ChatRepository {

    override fun loadChat(chatId: String): Flow<List<ChatMessageModel>> = callbackFlow {
        chatsDatabase.child(chatId)
            .child(MESSAGES_PATH)
            .addListenerAsFlow(this)
    }.map(::mapMessages).flowOn(dispatcher)

    private fun mapMessages(snapshot: DataSnapshot): List<ChatMessageModel> {
        return snapshot.children.mapNotNull { result ->
            val messageDto = result.getValue(ChatMessageDto::class.java)
            val user = userService.getUserById(messageDto?.senderId)?.mapToModel()
            messageDto?.mapToModel(user)
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

    override suspend fun getSenderProfile(chatId: String): ChatFriendModel? {
        val currentUserId = getCurrentUserId()
        val userTask = usersDatabase.child(currentUserId)
            .child("chats")
            .child(chatId)
            .get()
        awaitTask(userTask)
        val chatReference = userTask.result.getValue(ChatReference::class.java)
        return userService.getUserById(chatReference?.friendId)?.mapToModel()
    }
}

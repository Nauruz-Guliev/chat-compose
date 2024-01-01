package ru.kpfu.itis.chat.data.repository

import android.security.keystore.UserNotAuthenticatedException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import ru.kpfu.itis.chat.data.mapper.mapToDto
import ru.kpfu.itis.chat.data.mapper.mapToModel
import ru.kpfu.itis.chat.data.model.ChatMessageDto
import ru.kpfu.itis.chat.domain.model.ChatMessageModel
import ru.kpfu.itis.chat.domain.repository.ChatRepository
import ru.kpfu.itis.core_data.UserService
import ru.kpfu.itis.core_data.di.ChatsDatabase
import ru.kpfu.itis.core_data.di.IoDispatcher
import javax.inject.Inject

private const val MESSAGES_PATH = "messages"

class ChatRepositoryImpl @Inject constructor(
    @ChatsDatabase
    private val database: DatabaseReference,
    private val firebaseAuth: FirebaseAuth,
    private val userService: UserService,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
) : ChatRepository {

    override fun loadChat(chatId: String): Flow<List<ChatMessageModel>> = callbackFlow {
        database.child(chatId)
            .child(MESSAGES_PATH)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.map { result ->
                            result.getValue(ChatMessageDto::class.java)
                        }.also { listOfMessages ->
                            trySend(listOfMessages)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        close(error.toException())
                    }
                }
            )
        awaitClose()
    }.map(::mapUserList).flowOn(dispatcher)

    private fun mapUserList(list: List<ChatMessageDto?>): List<ChatMessageModel> {
        return list.mapNotNull { messageDto ->
            val user = userService.getUserById(messageDto?.senderId)?.mapToModel()
            messageDto?.mapToModel(user)
        }
    }

    override suspend fun sendMessage(chatId: String, message: ChatMessageModel): Unit =
        withContext(dispatcher) {
            database.child(chatId)
                .child(MESSAGES_PATH)
                .child(message.time.toString())
                .setValue(message.mapToDto())
                .await()
        }

    override fun getCurrentUserId(): String {
        return firebaseAuth.currentUser?.uid ?: throw UserNotAuthenticatedException()
    }
}

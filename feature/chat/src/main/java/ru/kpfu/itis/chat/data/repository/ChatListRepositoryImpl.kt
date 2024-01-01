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
import ru.kpfu.itis.chat.data.mapper.mapToModel
import ru.kpfu.itis.chat.data.model.ChatListResponse
import ru.kpfu.itis.chat.domain.model.ChatListModel
import ru.kpfu.itis.chat.domain.repository.ChatListRepository
import ru.kpfu.itis.chat_api.ChatReference
import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.core_data.UserService
import ru.kpfu.itis.core_data.di.IoDispatcher
import ru.kpfu.itis.core_data.di.UsersDatabase
import javax.inject.Inject

class ChatListRepositoryImpl @Inject constructor(
    @UsersDatabase
    private val databaseReference: DatabaseReference,
    private val firebaseAuth: FirebaseAuth,
    private val userService: UserService,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
) : ChatListRepository {

    override fun loadChatList(): Flow<List<ChatListModel>> = callbackFlow {
        val currentUserId = firebaseAuth.currentUser?.uid ?: throw UserNotAuthenticatedException()
        databaseReference.child(currentUserId).child("chats")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val chatList = snapshot.children.map { result ->
                        val chatReference = result.getValue(ChatReference::class.java)
                        val chatId = result.key
                        val friend = getChatUser(chatReference?.friendId)
                        ChatListResponse(
                            friend = friend,
                            chatId = chatId,
                            lastUpdated = System.currentTimeMillis()
                        )
                    }
                    trySend(chatList)
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            })
        awaitClose()
    }.map(List<ChatListResponse>::mapToModel).flowOn(dispatcher)


    override fun getChatUser(userId: String?): ChatUser? {
        if (userId == null) return null
        return userService.getUserById(userId)
    }
}
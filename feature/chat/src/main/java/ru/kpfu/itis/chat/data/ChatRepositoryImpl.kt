package ru.kpfu.itis.chat.data

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
import ru.kpfu.itis.chat.domain.model.ChatListModel
import ru.kpfu.itis.chat.domain.repository.ChatRepository
import ru.kpfu.itis.chat_api.ChatReference
import ru.kpfu.itis.core_data.UserService
import ru.kpfu.itis.core_data.di.IoDispatcher
import ru.kpfu.itis.core_data.di.UsersDatabase
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    @UsersDatabase
    private val databaseReference: DatabaseReference,
    private val firebaseAuth: FirebaseAuth,
    private val userService: UserService,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
) : ChatRepository {

    override fun loadChatList(): Flow<List<ChatListModel>> = callbackFlow {
        val currentUserId = firebaseAuth.currentUser?.uid ?: throw UserNotAuthenticatedException()
        databaseReference.child(currentUserId).child("chats")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val chatList = snapshot.children.map { result ->
                        val chatReference = result.getValue(ChatReference::class.java)
                        val chatId = result.key
                        val friend = userService.getUserById(chatReference?.friendId)
                        ChatListModel(
                            friend = friend,
                            chatId = chatId
                        )
                    }
                    trySend(chatList)
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            })
        awaitClose()
    }.flowOn(dispatcher)
}
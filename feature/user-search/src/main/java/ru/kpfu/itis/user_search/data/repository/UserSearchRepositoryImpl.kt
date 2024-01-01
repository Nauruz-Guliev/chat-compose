package ru.kpfu.itis.user_search.data.repository

import android.security.keystore.UserNotAuthenticatedException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import ru.kpfu.itis.chat_api.ChatReference
import ru.kpfu.itis.chat_api.ChatRoom
import ru.kpfu.itis.chat_api.emptyChatRoom
import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.core_data.di.ChatsDatabase
import ru.kpfu.itis.core_data.di.UsersDatabase
import ru.kpfu.itis.user_search.domain.repository.UserSearchRepository
import javax.inject.Inject

private const val CHATS_PATH_KEY = "chats"

class UserSearchRepositoryImpl @Inject constructor(
    @UsersDatabase
    private val userDatabase: DatabaseReference,
    @ChatsDatabase
    private val chatDatabase: DatabaseReference,
    private val firebaseAuth: FirebaseAuth,
) : UserSearchRepository {

    override fun findUser(name: String): Flow<List<ChatUser>> = callbackFlow {
        userDatabase.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val currentUserId = getCurrentUserId()
                    snapshot.children
                        .mapNotNull { dataSnapshot ->
                            dataSnapshot.child("profile")
                                .getValue(ChatUser::class.java)?.apply {
                                    id = dataSnapshot.key
                                }
                        }
                        .filter { it.id != currentUserId }
                        .filter { it.name?.contains(name, ignoreCase = true) == true }
                        .also { trySend(it) }
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }
        )
        awaitClose()
    }

    override suspend fun startChatting(userId: String) {
        val currentUserId = getCurrentUserId()
        val chatId = currentUserId + userId
        createChatReferenceForEachUser(userId, currentUserId, chatId)
        createChatReferenceForEachUser(currentUserId, userId, chatId)
        createChatRoom(chatId)
    }

    private fun getCurrentUserId(): String {
        return firebaseAuth.currentUser?.uid ?: throw UserNotAuthenticatedException()
    }

    override fun loadExistingChats(): Flow<List<String>> = callbackFlow {
        val currentUserId = getCurrentUserId()
        userDatabase.child(currentUserId)
            .child("profile")
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userIds = mutableListOf<String>()
                        snapshot.children.mapNotNull { result ->
                            userIds.clear()
                            result
                        }.forEach { snapShot ->
                            snapShot.children.mapNotNull {
                                it.getValue(ChatReference::class.java)?.friendId
                            }.forEach {
                                userIds.add(it)
                            }
                        }
                        trySend(userIds)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        close(error.toException())
                    }
                }
            )
        awaitClose()
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

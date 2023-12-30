package ru.kpfu.itis.user_search.data.repository

import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.core_data.di.UsersDatabase
import ru.kpfu.itis.user_search.domain.repository.UserSearchRepository
import javax.inject.Inject

class UserSearchRepositoryImpl @Inject constructor(
    @UsersDatabase
    private val databaseReference: DatabaseReference
) : UserSearchRepository {

    override fun findUser(name: String): Flow<List<ChatUser>> = flow {
        val userListTask = databaseReference.get()
        Tasks.await(userListTask)
        userListTask.result.children
            .mapNotNull { dataSnapshot ->
                dataSnapshot.getValue(ChatUser::class.java)?.apply {
                    id = dataSnapshot.key
                }
            }
            .filter { it.name?.contains(name) == true }
            .also { emit(it) }
    }
}

/*.also { chatUsers ->
            Log.d("NEW_USERS", chatUsers.toString())
            emit(chatUsers.filter { it.name?.contains(name) == true })
        }

 */

/*
databaseReference.addListenerForSingleValueEvent(
    object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val users = mutableListOf<ChatUser>()
            snapshot.children.forEach { usersSnapshot ->
                usersSnapshot.getValue(ChatUser::class.java)?.let { chatUser ->
                    users.add(chatUser)
                }
            }
            Log.d("FIND_USER_SUCCESS", users.toString())
            trySendBlocking(users)
            close()
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e("FIND_USER_ERROR", error.message)
            close(error.toException())
        }
    }
)

 */

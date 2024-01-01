package ru.kpfu.itis.core_data

import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import ru.kpfu.itis.core_data.di.UsersDatabase
import javax.inject.Inject

class UserService @Inject constructor(
    @UsersDatabase
    private val databaseReference: DatabaseReference
) {

    suspend fun saveUser(user: ChatUser, uid: String): Boolean {
        return databaseReference.child(uid).setValue(user).also {
            it.await()
        }.isSuccessful
    }

    fun getUserById(uid: String?): ChatUser? {
        if (uid == null) return null
        val task = databaseReference.child(uid).get()
        awaitTask(task)
        return task.result.getValue(ChatUser::class.java)
    }

    fun updateUser(chatUser: ChatUser, userUid: String?): Boolean {
        return userUid?.let {
            val user = databaseReference.child(it)
            val userUpdateTask = user.setValue(chatUser)
            awaitTask(userUpdateTask)
            userUpdateTask.isSuccessful
        } ?: false
    }
}

package ru.kpfu.itis.core_data

import com.google.android.gms.tasks.Tasks
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

    fun getUserById(uid: String): ChatUser? {
        val task = databaseReference.child(uid).get()
        Tasks.await(task)
        return task.result.getValue(ChatUser::class.java)
    }

    fun updateUser(chatUser: ChatUser, userUid: String?): Boolean {
        return userUid?.let {
            val user = databaseReference.child(it)
            val userUpdateTask = user.setValue(chatUser)
            Tasks.await(userUpdateTask)
            userUpdateTask.isSuccessful
        } ?: false
    }
}
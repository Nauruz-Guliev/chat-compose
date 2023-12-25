package ru.kpfu.itis.profile.data.repository

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.core_data.UserService
import ru.kpfu.itis.core_data.UserStore
import ru.kpfu.itis.profile.domain.exception.NotAuthenticatedException
import ru.kpfu.itis.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val userStore: UserStore,
    private val firebaseAuth: FirebaseAuth,
) : ProfileRepository {

    override suspend fun updateProfile(newEmail: String, name: String) {
        val updateRequest = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()
        val userTask = firebaseAuth.currentUser?.let { firebaseUser ->
            firebaseUser.updateEmail(newEmail)
            firebaseUser.updateProfile(updateRequest)
        }
        val userId = userStore.getUserId()
        userTask?.let { task ->
            Tasks.await(task)
            userService.updateUser(ChatUser(name), userId)
        } ?: throw NotAuthenticatedException()
    }

    override suspend fun getUser(): ChatUser? {
        return userStore.getUserId()?.let {
            Log.e("ID", it.toString())
            userService.getUserById(it).apply {
                Log.e("USER", this.toString())
            }
        }
    }
}

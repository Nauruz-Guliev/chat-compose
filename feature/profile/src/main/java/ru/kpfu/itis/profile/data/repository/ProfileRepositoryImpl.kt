package ru.kpfu.itis.profile.data.repository

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.core_data.UserService
import ru.kpfu.itis.core_data.UserStore
import ru.kpfu.itis.profile.domain.exception.NotAuthenticatedException
import ru.kpfu.itis.profile.domain.model.UpdateProfileModel
import ru.kpfu.itis.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val userStore: UserStore,
    private val firebaseAuth: FirebaseAuth,
) : ProfileRepository {

    override suspend fun updateProfile(
        userModel: UpdateProfileModel
    ) {
        val updateRequest = UserProfileChangeRequest.Builder()
            .setDisplayName(userModel.name)
            .build()
        val userTask = firebaseAuth.currentUser?.let { firebaseUser ->
            firebaseUser.run {
                updatePassword(userModel.password)
                updateEmail(userModel.email)
                updateProfile(updateRequest)
            }
        }
        val userId = userStore.getUserId()
        userTask?.let { task ->
            Tasks.await(task)
            userService.updateUser(
                ChatUser(name = userModel.name, email = userModel.email, profileImage = userModel.profileImage),
                userId
            )
        } ?: throw NotAuthenticatedException()
    }

    override suspend fun getUser(): ChatUser? {
        return userStore.getUserId()?.let {
            userService.getUserById(it)
        }
    }

    override suspend fun clearUserId() {
        userStore.clearId()
    }
}

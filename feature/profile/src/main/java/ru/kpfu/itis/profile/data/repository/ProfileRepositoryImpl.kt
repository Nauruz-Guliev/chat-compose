package ru.kpfu.itis.profile.data.repository

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import ru.kpfu.itis.chat_api.ClearChatListAction
import ru.kpfu.itis.chat_api.ClearChatMessagesAction
import ru.kpfu.itis.coredata.ChatUser
import ru.kpfu.itis.coredata.UserService
import ru.kpfu.itis.coredata.UserStore
import ru.kpfu.itis.coredata.di.IoDispatcher
import ru.kpfu.itis.profile.domain.model.UpdateProfileModel
import ru.kpfu.itis.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val userStore: UserStore,
    private val firebaseAuth: FirebaseAuth,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val clearChatListAction: ClearChatListAction,
    private val clearChatMessagesAction: ClearChatMessagesAction
) : ProfileRepository {

    override suspend fun updateProfile(
        model: UpdateProfileModel
    ): Unit = withContext(dispatcher) {
        val updateRequest = UserProfileChangeRequest.Builder()
            .setDisplayName(model.name)
            .build()
        firebaseAuth.currentUser?.run {
            Tasks.whenAllSuccess<Unit>(
                updateEmail(model.email),
                updateProfile(updateRequest)
            ).await()
            userService.updateUser(
                ChatUser(
                    name = model.name,
                    email = model.email,
                    profileImage = model.profileImage,
                    id = uid
                )
            )
        }
    }

    override suspend fun getUser(): ChatUser? = withContext(dispatcher) {
        userStore.getUserId()?.let {
            userService.getUserById(it)
        }
    }

    override suspend fun clearUserId() = withContext(dispatcher) {
        userStore.clearId()
    }

    override suspend fun signOut() = withContext(dispatcher) {
        firebaseAuth.signOut()
    }

    override suspend fun clearCachedChatList() {
        clearChatListAction.clearChatList()
    }

    override suspend fun clearCachedChatMessages() {
        clearChatMessagesAction.clearChatMessages()
    }
}

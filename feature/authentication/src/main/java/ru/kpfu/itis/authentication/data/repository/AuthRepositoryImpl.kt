package ru.kpfu.itis.authentication.data.repository

import android.security.keystore.UserNotAuthenticatedException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ru.kpfu.itis.authentication.data.mapper.mapToLocalUser
import ru.kpfu.itis.authentication.data.mapper.toChatUser
import ru.kpfu.itis.authentication.domain.model.User
import ru.kpfu.itis.authentication.domain.repository.AuthRepository
import ru.kpfu.itis.coredata.UserService
import ru.kpfu.itis.coredata.UserStore
import ru.kpfu.itis.coredata.awaitTask
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userService: UserService,
    private val userStore: UserStore,
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun signIn(user: User): User {
        val userSignInTask = firebaseAuth.signInWithEmailAndPassword(
            user.email.orEmpty(),
            user.password
        )
        awaitTask(userSignInTask)
        userStore.saveUserId(currentUser?.uid)
        return userSignInTask.result.user?.mapToLocalUser()
            ?: throw UserNotAuthenticatedException()
    }

    override suspend fun signUp(user: User): User {
        val userCreationTask = firebaseAuth.createUserWithEmailAndPassword(
            user.email.orEmpty(),
            user.password
        )
        awaitTask(userCreationTask)
        userCreationTask.result.user?.uid?.let { userUid ->
            userService.saveUser(user.toChatUser(userUid))
            userStore.saveUserId(userUid)
        }
        signIn(user)
        return userCreationTask.result.user?.mapToLocalUser()
            ?: throw UserNotAuthenticatedException()
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }
}

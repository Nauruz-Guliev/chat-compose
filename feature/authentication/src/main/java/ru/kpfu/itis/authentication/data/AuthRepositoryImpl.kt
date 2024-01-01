package ru.kpfu.itis.authentication.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import ru.kpfu.itis.authentication.domain.model.User
import ru.kpfu.itis.authentication.domain.repository.AuthRepository
import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.core_data.UserService
import ru.kpfu.itis.core_data.UserStore
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userService: UserService,
    private val userStore: UserStore,
) : AuthRepository<User> {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun signIn(email: String, password: String): User {
        val userSignInTask = firebaseAuth.signInWithEmailAndPassword(email, password).also {
            it.await()
        }
        userStore.saveUserId(currentUser?.uid)
        return with(userSignInTask.result.user) {
            User(
                name = this?.displayName,
                email = email,
            )
        }
    }

    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): User {
        val userCreationTask = firebaseAuth.createUserWithEmailAndPassword(email, password).also {
            it.await()
        }
        userCreationTask.result.user?.uid?.let { userUid ->
            userService.saveUser(ChatUser(name = name, email = email), userUid)
            userStore.saveUserId(userUid)
        }
        signIn(email, password)
        firebaseAuth.currentUser
        return with(userCreationTask.result.user) {
            User(
                name = this?.displayName,
                email = email
            )
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }
}

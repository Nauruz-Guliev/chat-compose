package ru.kpfu.itis.authentication.data

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ru.kpfu.itis.authentication.domain.model.User
import ru.kpfu.itis.authentication.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthRepository<User> {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun signIn(email: String, password: String): User {
        val task = firebaseAuth.signInWithEmailAndPassword(email, password)
        Tasks.await(task)
        return with(task.result.user) {
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
        val task = firebaseAuth.createUserWithEmailAndPassword(email, password)
        Tasks.await(task)
        return with(task.result.user) {
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

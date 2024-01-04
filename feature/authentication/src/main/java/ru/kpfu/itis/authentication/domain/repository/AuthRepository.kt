package ru.kpfu.itis.authentication.domain.repository

import ru.kpfu.itis.authentication.domain.model.User

interface AuthRepository {
    val currentUser: Any?

    suspend fun signIn(user: User): User
    suspend fun signUp(user: User): User
    suspend fun logout()
}

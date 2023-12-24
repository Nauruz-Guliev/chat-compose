package ru.kpfu.itis.authentication.domain.repository

interface AuthRepository<User> {
    val currentUser: Any?
    suspend fun signIn(email: String, password: String): User
    suspend fun signUp(name: String, email: String, password: String): User
    suspend fun logout()
}
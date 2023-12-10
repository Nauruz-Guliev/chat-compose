package ru.kpfu.itis.authentication.domain.repository

interface AuthRepository<User> {
    val currentUser: Any?
    suspend fun login(email: String, password: String): Result<User>
    suspend fun signUp(name: String, email: String, password: String): Result<User>
    suspend fun logout()
}
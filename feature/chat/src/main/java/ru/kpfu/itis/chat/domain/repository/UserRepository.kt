package ru.kpfu.itis.chat.domain.repository

interface UserRepository {
    suspend fun getAllUsers() : List<Any>
}
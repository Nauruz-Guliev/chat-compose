package ru.kpfu.itis.authentication.domain.model

data class User(
    val name: String? = null,
    val password: String? = null,
    val email: String? = null
)
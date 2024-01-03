package ru.kpfu.itis.authentication.domain.model

data class User(
    val name: String? = null,
    val password: String = "",
    val email: String?
)
package ru.kpfu.itis.usersearch.domain.model

data class User(
    var id: String = "",
    val name: String? = null,
    val profileImage: String? = null,
)

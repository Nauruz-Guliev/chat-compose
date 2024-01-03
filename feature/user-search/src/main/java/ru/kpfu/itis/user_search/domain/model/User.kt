package ru.kpfu.itis.user_search.domain.model

data class User(
    var id: String = "",
    val name: String? = null,
    val profileImage: String? = null,
)


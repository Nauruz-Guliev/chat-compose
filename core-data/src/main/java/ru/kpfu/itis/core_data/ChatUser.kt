package ru.kpfu.itis.core_data

data class ChatUser(
    var id: String = "",
    val name: String? = null,
    val profileImage: String? = null,
    val email: String? = null,
)
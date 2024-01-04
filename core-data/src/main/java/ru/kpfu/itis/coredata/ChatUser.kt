package ru.kpfu.itis.coredata

data class ChatUser(
    var id: String = "",
    val name: String? = null,
    val profileImage: String? = null,
    val email: String? = null,
)

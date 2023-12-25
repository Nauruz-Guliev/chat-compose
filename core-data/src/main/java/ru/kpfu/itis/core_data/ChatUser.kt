package ru.kpfu.itis.core_data

data class ChatUser(
    val name: String? = null,
    val profileImage: String? = null,
    val email: String? = null,
    val friendsList: List<Int> = emptyList()
) {
}
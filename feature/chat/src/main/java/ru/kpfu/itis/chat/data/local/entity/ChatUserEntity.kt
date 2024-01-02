package ru.kpfu.itis.chat.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_user")
data class ChatUserEntity(
    @PrimaryKey
    var userId: String,
    val name: String? = null,
    val profileImage: String? = null,
)
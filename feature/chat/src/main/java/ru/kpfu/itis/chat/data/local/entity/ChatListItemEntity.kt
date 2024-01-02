package ru.kpfu.itis.chat.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_list")
data class ChatListItemEntity(
    @PrimaryKey
    val chatId: String,
    val senderId: String? = null,
    val lastUpdated: Long = 0
)
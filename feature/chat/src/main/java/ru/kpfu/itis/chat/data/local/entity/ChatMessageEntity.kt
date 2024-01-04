package ru.kpfu.itis.chat.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_message")
data class ChatMessageEntity(
    @PrimaryKey
    val time: Long,
    @Embedded
    val sender: ChatUserEntity? = null,
    val text: String? = null,
    val chatId: String? = null,
)

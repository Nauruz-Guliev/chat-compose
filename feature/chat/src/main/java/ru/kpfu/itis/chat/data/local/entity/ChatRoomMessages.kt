package ru.kpfu.itis.chat.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ChatRoomMessages(
    @Embedded val chat: ChatListItemEntity,
    @Relation(
        parentColumn = "chatId",
        entityColumn = "chatId"
    )
    val messages: List<ChatMessageEntity> = emptyList()
)
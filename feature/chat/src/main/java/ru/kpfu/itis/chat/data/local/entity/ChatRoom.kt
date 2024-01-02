package ru.kpfu.itis.chat.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ChatRoom(
    @Embedded val user: ChatUserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "senderId"
    )
    val chatListEntity: ChatListEntity
)
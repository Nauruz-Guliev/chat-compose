package ru.kpfu.itis.chat.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kpfu.itis.chat.data.local.dao.ChatListDao
import ru.kpfu.itis.chat.data.local.dao.ChatMessagesDao
import ru.kpfu.itis.chat.data.local.entity.ChatListItemEntity
import ru.kpfu.itis.chat.data.local.entity.ChatMessageEntity
import ru.kpfu.itis.chat.data.local.entity.ChatUserEntity

@Database(
    entities = [
        ChatListItemEntity::class,
        ChatUserEntity::class,
        ChatMessageEntity::class
    ],
    version = 3
)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun chatListDao(): ChatListDao
    abstract fun chatMessagesDao(): ChatMessagesDao
}
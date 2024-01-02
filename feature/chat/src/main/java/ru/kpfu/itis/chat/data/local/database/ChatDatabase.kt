package ru.kpfu.itis.chat.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kpfu.itis.chat.data.local.dao.ChatListDao
import ru.kpfu.itis.chat.data.local.entity.ChatListEntity
import ru.kpfu.itis.chat.data.local.entity.ChatUserEntity

@Database(
    entities = [
        ChatListEntity::class,
        ChatUserEntity::class,
    ],
    version = 1
)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun chatListDao(): ChatListDao
}
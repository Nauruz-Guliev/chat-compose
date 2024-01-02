package ru.kpfu.itis.chat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.chat.data.local.entity.ChatListItemEntity
import ru.kpfu.itis.chat.data.local.entity.ChatRoom
import ru.kpfu.itis.chat.data.local.entity.ChatUserEntity

@Dao
interface ChatListDao {

    @Transaction
    @Query("SELECT * FROM chat_user ")
    fun getCachedChatList(): Flow<List<ChatRoom>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg chat: ChatListItemEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg chat: ChatUserEntity)

    @Query("DELETE FROM chat_user")
    suspend fun clearUsers()


    @Query("DELETE FROM chat_list")
    suspend fun clearChatList()

    suspend fun clearDatabase() {
        clearUsers()
        clearChatList()
    }
}
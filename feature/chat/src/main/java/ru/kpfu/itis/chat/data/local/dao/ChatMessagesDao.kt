package ru.kpfu.itis.chat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.chat.data.local.entity.ChatMessageEntity
import ru.kpfu.itis.chat.data.local.entity.ChatRoomMessages

@Dao
interface ChatMessagesDao {

    @Transaction
    @Query("SELECT * FROM chat_list WHERE chat_list.chatId =:chatId")
    fun getCachedMessages(chatId: String): Flow<ChatRoomMessages>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg chat: ChatMessageEntity)


    @Query("DELETE FROM chat_message")
    suspend fun clearAllMessages()
}
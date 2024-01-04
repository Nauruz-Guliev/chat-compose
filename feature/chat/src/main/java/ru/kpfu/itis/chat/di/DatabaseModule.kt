package ru.kpfu.itis.chat.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.chat.data.local.dao.ChatListDao
import ru.kpfu.itis.chat.data.local.dao.ChatMessagesDao
import ru.kpfu.itis.chat.data.local.database.ChatDatabase

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideRoomDatabase(context: Application): ChatDatabase {
        return Room.databaseBuilder(context, ChatDatabase::class.java, "chat_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideChatListDao(db: ChatDatabase): ChatListDao {
        return db.chatListDao()
    }

    @Provides
    fun provideChatMessagesDao(db: ChatDatabase): ChatMessagesDao {
        return db.chatMessagesDao()
    }
}

package ru.kpfu.itis.coredata.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @UsersDatabase
    fun provideUsersDatabaseReference(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference.child("Users")
    }

    @Provides
    @ChatsDatabase
    fun provideChatsDatabaseReference(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference.child("Chats")
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ChatsDatabase

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UsersDatabase

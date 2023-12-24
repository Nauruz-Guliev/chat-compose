package ru.kpfu.itis.core_data.di

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
    fun provideUsersDatabaseReference() : DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("Users")
    }
}

@Retention(AnnotationRetention.SOURCE)
@Qualifier
annotation class UsersDatabase
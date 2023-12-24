package ru.kpfu.itis.core_data

import com.google.firebase.database.DatabaseReference
import ru.kpfu.itis.core_data.di.UsersDatabase
import javax.inject.Inject

class UserService @Inject constructor(
    @UsersDatabase
    private val databaseReference: DatabaseReference
) {

    fun saveUser(user: ChatUser): String? {
        val userPush = databaseReference.push()
        userPush.setValue(user)
        return userPush.key
    }
}
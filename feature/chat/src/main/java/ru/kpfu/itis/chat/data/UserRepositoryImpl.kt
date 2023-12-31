package ru.kpfu.itis.chat.data

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import ru.kpfu.itis.chat.domain.repository.UserRepository
import ru.kpfu.itis.core_data.di.UsersDatabase
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @UsersDatabase
    private val databaseReference: DatabaseReference
): UserRepository {

    override suspend fun getAllUsers(): List<Any> {
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    Log.e("CHILDREN", it.value.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ERROR", error.message)
            }
        })
        return emptyList()
    }
}
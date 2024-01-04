package ru.kpfu.itis.coredata

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * Executes blocking Firebase tasks on IO Thread Pool.
 */
fun <T> awaitTask(task: Task<T>): T = runBlocking {
    withContext(Dispatchers.IO) {
        Tasks.await(task)
    }
}

/**
 * Adds listener to Firebase DatabaseReference and emits received values to flow.
 */
fun DatabaseReference.addListenerAsFlow(): Flow<DataSnapshot> {
    return callbackFlow {
        this@addListenerAsFlow.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    trySend(snapshot)
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }
        )
        awaitClose()
    }
}

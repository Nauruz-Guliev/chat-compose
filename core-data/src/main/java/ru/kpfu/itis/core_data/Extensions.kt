package ru.kpfu.itis.core_data

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
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
 * Listens for changes in realtime database and emits them to the given coroutine flow scope.
 */
suspend fun DatabaseReference.addListenerAsFlow(producer: ProducerScope<DataSnapshot>) {
    this.addValueEventListener(
        object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                producer.trySend(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                producer.close(error.toException())
            }
        }
    )
    producer.awaitClose()
}
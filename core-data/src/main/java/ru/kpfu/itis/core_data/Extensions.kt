package ru.kpfu.itis.core_data

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun <T> awaitTask(task: Task<T>): T = runBlocking {
    withContext(Dispatchers.IO) {
        Tasks.await(task)
    }
}
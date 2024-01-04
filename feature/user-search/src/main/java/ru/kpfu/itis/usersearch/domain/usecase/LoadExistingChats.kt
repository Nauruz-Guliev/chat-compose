package ru.kpfu.itis.usersearch.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.usersearch.domain.repository.UserSearchRepository
import javax.inject.Inject

class LoadExistingChats @Inject constructor(
    private val repository: UserSearchRepository
) {
    operator fun invoke(): Flow<List<String>> {
        return repository.loadExistingChats()
    }
}

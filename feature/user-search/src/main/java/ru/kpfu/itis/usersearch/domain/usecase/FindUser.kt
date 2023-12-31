package ru.kpfu.itis.usersearch.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.usersearch.domain.model.User
import ru.kpfu.itis.usersearch.domain.repository.UserSearchRepository
import javax.inject.Inject

class FindUser @Inject constructor(
    private val repositoryImpl: UserSearchRepository
) {

    operator fun invoke(named: String): Flow<List<User>> {
        return repositoryImpl.findUser(named)
    }
}

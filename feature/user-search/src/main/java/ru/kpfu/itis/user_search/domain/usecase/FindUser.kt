package ru.kpfu.itis.user_search.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.user_search.data.repository.UserSearchRepositoryImpl
import javax.inject.Inject

class FindUser @Inject constructor(
    private val repositoryImpl: UserSearchRepositoryImpl
) {

    operator fun invoke(named: String): Flow<List<ChatUser>> {
        return repositoryImpl.findUser(named)
            .flowOn(Dispatchers.IO)
    }
}

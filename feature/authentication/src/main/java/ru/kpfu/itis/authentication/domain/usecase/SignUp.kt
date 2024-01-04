package ru.kpfu.itis.authentication.domain.usecase

import ru.kpfu.itis.authentication.domain.model.User
import ru.kpfu.itis.authentication.domain.repository.AuthRepository
import javax.inject.Inject

class SignUp @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(user: User): User {
        return repository.signUp(user)
    }
}

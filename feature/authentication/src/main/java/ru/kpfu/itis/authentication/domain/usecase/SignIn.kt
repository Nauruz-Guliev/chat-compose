package ru.kpfu.itis.authentication.domain.usecase

import ru.kpfu.itis.authentication.domain.model.User
import ru.kpfu.itis.authentication.domain.repository.AuthRepository
import javax.inject.Inject

class SignIn @Inject constructor(
    private val repository: AuthRepository<User>
) {
    suspend operator fun invoke(email: String, password: String): User {
        return repository.signIn(email, password)
    }
}
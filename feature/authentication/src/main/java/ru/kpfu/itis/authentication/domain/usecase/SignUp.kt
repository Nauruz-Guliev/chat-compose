package ru.kpfu.itis.authentication.domain.usecase

import ru.kpfu.itis.authentication.domain.model.User
import ru.kpfu.itis.authentication.domain.repository.AuthRepository
import javax.inject.Inject

class SignUp @Inject constructor(
    private val repository: AuthRepository<User>
) {
    suspend operator fun invoke(name: String, email: String, password: String): User {
        return repository.signUp(name, email, password)
    }
}
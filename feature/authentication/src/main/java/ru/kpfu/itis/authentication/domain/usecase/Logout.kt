package ru.kpfu.itis.authentication.domain.usecase

import ru.kpfu.itis.authentication.domain.repository.AuthRepository
import javax.inject.Inject

class Logout @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() {
        return repository.logout()
    }
}
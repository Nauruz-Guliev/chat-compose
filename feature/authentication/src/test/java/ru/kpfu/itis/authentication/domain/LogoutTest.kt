package ru.kpfu.itis.authentication.domain

import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coJustRun
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.kpfu.itis.authentication.domain.model.User
import ru.kpfu.itis.authentication.domain.repository.AuthRepository
import ru.kpfu.itis.authentication.domain.usecase.Logout
import ru.kpfu.itis.core.testing.Then
import ru.kpfu.itis.core.testing.When

class LogoutTest : BehaviorSpec( {

    val repository = mockk<AuthRepository<User>>()
    val logout = Logout(repository)

    Given("should call repository when logout action occurs") {
        coJustRun { repository.logout() }
        When {
            logout.invoke()
            Then {
                coVerifySequence {
                    repository.logout()
                }
            }
        }
    }
})
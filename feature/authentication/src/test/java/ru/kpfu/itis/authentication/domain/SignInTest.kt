package ru.kpfu.itis.authentication.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.kpfu.itis.authentication.domain.model.User
import ru.kpfu.itis.authentication.domain.repository.AuthRepository
import ru.kpfu.itis.authentication.domain.usecase.SignIn
import ru.kpfu.itis.core_ui.testing.Then
import ru.kpfu.itis.core_ui.testing.When

class SignInTest : BehaviorSpec( {

    val repository = mockk<AuthRepository<User>>()
    val signIn = SignIn(repository)

    Given("should call repository when signIn method invoked") {
        val expectedUser = user
        coEvery { repository.signIn(EMAIL, PASSWORD) } returns expectedUser
        When {
            val result = signIn.invoke(EMAIL, PASSWORD)
            Then {
                coVerifySequence {
                    repository.signIn(EMAIL, PASSWORD)
                    result shouldBe expectedUser
                }
            }
        }
    }

    Given("should call repository and throw error") {
        val expectedException = Exception()
        coEvery { repository.signIn(EMAIL, PASSWORD) } throws expectedException
        When {
            val exception = shouldThrow<Exception> {
                repository.signIn(EMAIL, PASSWORD)
            }
            Then {
                exception shouldBe expectedException
            }
        }
    }
})

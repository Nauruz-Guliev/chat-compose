package ru.kpfu.itis.authentication.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.kpfu.itis.authentication.domain.repository.AuthRepository
import ru.kpfu.itis.authentication.domain.usecase.SignUp
import ru.kpfu.itis.core_testing.Then
import ru.kpfu.itis.core_testing.When

class SignUpTest : BehaviorSpec({

    val repository = mockk<AuthRepository>()
    val signUp = SignUp(repository)

    Given("should call repository when signUp() invoked") {
        val expectedUser = user
        coEvery { repository.signUp(expectedUser) } returns expectedUser
        When {
            val result = signUp.invoke(expectedUser)
            Then {
                coVerifySequence {
                    repository.signUp(expectedUser)
                    result shouldBe expectedUser
                }
            }
        }
    }

    Given("should call repository and throw exception") {
        val expectedException = Exception()
        coEvery { repository.signUp(user) } throws expectedException
        When {
            val exception = shouldThrow<Exception> {
                repository.signUp(user)
            }
            Then {
                exception shouldBe expectedException
            }
        }
    }
})

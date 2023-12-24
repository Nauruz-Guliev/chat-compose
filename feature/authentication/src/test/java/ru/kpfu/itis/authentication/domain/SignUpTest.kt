package ru.kpfu.itis.authentication.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.kpfu.itis.authentication.domain.model.User
import ru.kpfu.itis.authentication.domain.repository.AuthRepository
import ru.kpfu.itis.authentication.domain.usecase.SignUp
import ru.kpfu.itis.core_testing.Then
import ru.kpfu.itis.core_testing.When

class SignUpTest : BehaviorSpec( {

    val repository = mockk<AuthRepository<User>>()
    val signUp = SignUp(repository)

    Given("should call repository when signUp method invoked") {
        val expectedUser = user
        coEvery { repository.signUp(NAME, EMAIL,PASSWORD) } returns expectedUser
        When {
            val result = signUp.invoke(NAME, EMAIL,PASSWORD)
            Then {
                coVerifySequence {
                    repository.signUp(NAME, EMAIL, PASSWORD)
                    result shouldBe expectedUser
                }
            }
        }
    }

    Given("should call repository and throw exception") {
        val expectedException = Exception()
        coEvery { repository.signUp(NAME, EMAIL, PASSWORD) } throws expectedException
        When {
            val exception = shouldThrow<Exception> {
                repository.signUp(NAME, EMAIL, PASSWORD)
            }
            Then {
                exception shouldBe expectedException
            }
        }
    }
})

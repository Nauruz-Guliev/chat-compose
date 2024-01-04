package ru.kpfu.itis.authentication.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.kpfu.itis.authentication.domain.repository.AuthRepository
import ru.kpfu.itis.authentication.domain.usecase.SignIn
import ru.kpfu.itis.coretesting.Then
import ru.kpfu.itis.coretesting.When

class SignInTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val repository = mockk<AuthRepository>()
    val signIn = SignIn(repository)

    Given("should call repository when signIn()") {
        val expectedUser = user
        coEvery { repository.signIn(expectedUser) } returns expectedUser
        When {
            val result = signIn.invoke(expectedUser)
            Then {
                coVerifySequence {
                    repository.signIn(expectedUser)
                }
                result shouldBe expectedUser
            }
        }
    }

    Given("should call repository and throw error") {
        val expectedException = Exception()
        coEvery { repository.signIn(user) } throws expectedException
        When {
            val exception = shouldThrow<Exception> {
                repository.signIn(user)
            }
            Then {
                coVerifySequence {
                    repository.signIn(user)
                }
                exception shouldBe expectedException
            }
        }
    }
})

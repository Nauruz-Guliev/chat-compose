package ru.kpfu.itis.usersearch.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.kpfu.itis.coretesting.Then
import ru.kpfu.itis.coretesting.When
import ru.kpfu.itis.usersearch.domain.repository.UserSearchRepository
import ru.kpfu.itis.usersearch.domain.usecase.FindUser

class FindUserTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val repository = mockk<UserSearchRepository>()
    val findUser = FindUser(repository)

    Given("should call repository when findUser() invoked") {
        val users = getUsers()
        coEvery { repository.findUser(USER_NAME) } returns users
        When {
            val foundUsers = findUser.invoke(USER_NAME)
            Then {
                coVerifySequence {
                    repository.findUser(USER_NAME)
                }
                foundUsers shouldBe users
            }
        }
    }

    Given("should call repository and throw error when findUser() invoked") {
        val expectedException = Exception()
        coEvery { repository.findUser(USER_NAME) } throws expectedException
        When {
            val exception = shouldThrow<Exception> {
                findUser(USER_NAME)
            }
            Then {
                coVerifySequence {
                    repository.findUser(USER_NAME)
                }
                exception shouldBe expectedException
            }
        }
    }
})

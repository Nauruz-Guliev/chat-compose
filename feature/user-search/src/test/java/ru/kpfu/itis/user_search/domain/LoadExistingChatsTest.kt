package ru.kpfu.itis.user_search.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.kpfu.itis.core_testing.Then
import ru.kpfu.itis.core_testing.When
import ru.kpfu.itis.user_search.domain.repository.UserSearchRepository
import ru.kpfu.itis.user_search.domain.usecase.LoadExistingChats

class LoadExistingChatsTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val repository = mockk<UserSearchRepository>()
    val loadExistingChats = LoadExistingChats(repository)

    Given("should call repository when loadExistingChats() invoked") {
        val chats = getExistingChats()
        coEvery { repository.loadExistingChats() } returns chats
        When {
            loadExistingChats.invoke()
            Then {
                coVerifySequence {
                    repository.loadExistingChats()
                }
            }
        }
    }

    Given("should call repository and throw error when loadExistingChats() invoked") {
        val expectedException = Exception()
        coEvery { repository.loadExistingChats() } throws expectedException
        When {
            val exception = shouldThrow<Exception> {
                loadExistingChats()
            }
            Then {
                coVerifySequence {
                    repository.loadExistingChats()
                }
                exception shouldBe expectedException
            }
        }
    }
})

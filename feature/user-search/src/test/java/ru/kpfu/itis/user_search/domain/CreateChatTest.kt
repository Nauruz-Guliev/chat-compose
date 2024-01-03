package ru.kpfu.itis.user_search.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.kpfu.itis.core_testing.Then
import ru.kpfu.itis.core_testing.When
import ru.kpfu.itis.user_search.domain.repository.UserSearchRepository
import ru.kpfu.itis.user_search.domain.usecase.CreateChat

class CreateChatTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val repository = mockk<UserSearchRepository>()
    val createChat = CreateChat(repository)

    Given("should call repository when createChat() invoked") {
        coJustRun { repository.createChat(USER_ID) }
        When {
            createChat.invoke(USER_ID)
            Then {
                coVerifySequence {
                    repository.createChat(USER_ID)
                }
            }
        }
    }

    Given("should call repository and throw error when createChat() invoked") {
        val expectedException = Exception()
        coEvery { repository.createChat(USER_ID) } throws expectedException
        When {
            val exception = shouldThrow<Exception> {
                createChat(USER_ID)
            }
            Then {
                coVerifySequence {
                    repository.createChat(USER_ID)
                }
                exception shouldBe expectedException
            }
        }
    }
})

package ru.kpfu.itis.chat.domain.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.kpfu.itis.chat.domain.repository.ChatRepository
import ru.kpfu.itis.coretesting.Then
import ru.kpfu.itis.coretesting.When

class GetCurrentUserIdTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val repository = mockk<ChatRepository>()
    val getChatListTest = GetCurrentUserId(repository)

    Given("should call repository when getCurrentUserId() called") {
        coEvery { repository.getCurrentUserId() } returns USER_ID
        When {
            val actual = getChatListTest.invoke()
            Then {
                coVerifySequence {
                    repository.getCurrentUserId()
                }
                actual shouldBe USER_ID
            }
        }
    }

    Given("should throw exception when getCurrentUserId() called and repo throws exception") {
        val exception = Exception()
        coEvery { repository.getCurrentUserId() } throws exception
        When {
            val actualException = shouldThrow<Exception> {
                getChatListTest.invoke()
            }
            Then {
                coVerifySequence {
                    repository.getCurrentUserId()
                }
                actualException shouldBe exception
            }
        }
    }
})

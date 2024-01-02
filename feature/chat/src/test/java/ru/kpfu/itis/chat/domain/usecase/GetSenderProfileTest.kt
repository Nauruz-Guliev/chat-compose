package ru.kpfu.itis.chat.domain.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.kpfu.itis.chat.domain.repository.ChatRepository
import ru.kpfu.itis.core_testing.Then
import ru.kpfu.itis.core_testing.When

class GetSenderProfileTest : BehaviorSpec({

    val repository = mockk<ChatRepository>()
    val getSenderProfile = GetSenderProfile(repository)

    Given("should call repository when getSenderProfile() called") {
        val messages = getUser()
        coEvery { repository.getSenderProfile(CHAT_ID) } returns messages
        When {
            val actual = getSenderProfile.invoke(CHAT_ID)
            Then {
                coVerifySequence {
                    repository.getSenderProfile(CHAT_ID)
                }
                actual shouldBe messages
            }
        }
    }

    Given("should throw exception when getSenderProfile() called and repo throws exception") {
        val exception = Exception()
        coEvery { repository.getSenderProfile(CHAT_ID) } throws exception
        When {
            val actualException = shouldThrow<Exception> {
                getSenderProfile.invoke(CHAT_ID)
            }
            Then {
                actualException shouldBe exception
            }
        }
    }
})
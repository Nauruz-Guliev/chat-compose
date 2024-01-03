package ru.kpfu.itis.chat.domain.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.kpfu.itis.chat.domain.repository.ChatRepository
import ru.kpfu.itis.core_testing.Then
import ru.kpfu.itis.core_testing.When

class GetMessagesTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val repository = mockk<ChatRepository>()
    val getMessages = GetMessages(repository)

    Given("should call repository when getMessages() called") {
        val messages = getMessagesFlow()
        coEvery { repository.loadChat(CHAT_ID) } returns messages
        When {
            val actual = getMessages.invoke(CHAT_ID)
            Then {
                coVerifySequence {
                    repository.loadChat(CHAT_ID)
                }
                actual shouldBe messages
            }
        }
    }

    Given("should throw exception when getMessages() called and repo throws exception") {
        val exception = Exception()
        coEvery { repository.loadChat(CHAT_ID) } throws exception
        When {
            val actualException = shouldThrow<Exception> {
                getMessages.invoke(CHAT_ID)
            }
            Then {
                coVerifySequence {
                    repository.loadChat(CHAT_ID)
                }
                actualException shouldBe exception
            }
        }
    }
})
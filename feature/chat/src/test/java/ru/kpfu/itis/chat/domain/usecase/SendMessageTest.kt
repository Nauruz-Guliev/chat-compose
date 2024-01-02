package ru.kpfu.itis.chat.domain.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.kpfu.itis.chat.domain.repository.ChatRepository
import ru.kpfu.itis.core_testing.Then
import ru.kpfu.itis.core_testing.When

class SendMessageTest : BehaviorSpec({

    val repository = mockk<ChatRepository>()
    val sendMessage = SendMessage(repository)

    Given("should call repository when sendMessage() called") {
        val message = getMessageModel()
        coJustRun { repository.sendMessage(CHAT_ID, message) }
        When {
            sendMessage.invoke(CHAT_ID, message)
            Then {
                coVerifySequence {
                    repository.sendMessage(CHAT_ID, message)
                }
            }
        }
    }

    Given("should throw exception when sendMessage() called and repo throws exception") {
        val exception = Exception()
        val message = getMessageModel()
        coEvery { repository.sendMessage(CHAT_ID, message) } throws exception
        When {
            val actualException = shouldThrow<Exception> {
                sendMessage.invoke(CHAT_ID, message)
            }
            Then {
                actualException shouldBe exception
            }
        }
    }
})
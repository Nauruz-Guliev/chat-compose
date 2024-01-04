package ru.kpfu.itis.chat.domain.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.kpfu.itis.chat.domain.repository.ChatListRepository
import ru.kpfu.itis.coretesting.Then
import ru.kpfu.itis.coretesting.When

class GetChatListTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val repository = mockk<ChatListRepository>()
    val getChatListTest = GetChatList(repository)

    Given("should call repository when getChatListTest() called") {
        val chatList = getChatListFlow()
        coEvery { repository.loadChatList() } returns chatList
        When {
            val actual = getChatListTest.invoke()
            Then {
                coVerifySequence {
                    repository.loadChatList()
                }
                actual shouldBe chatList
            }
        }
    }

    Given("should throw exception when getChatListTest() called and repo throws exception") {
        val exception = Exception()
        coEvery { repository.loadChatList() } throws exception
        When {
            val actualException = shouldThrow<Exception> {
                getChatListTest.invoke()
            }
            Then {
                coVerifySequence {
                    repository.loadChatList()
                }
                actualException shouldBe exception
            }
        }
    }
})

package ru.kpfu.itis.profile.domain.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.kpfu.itis.coretesting.Then
import ru.kpfu.itis.coretesting.When
import ru.kpfu.itis.profile.domain.repository.ProfileRepository

class ClearCachedDataTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val repository = mockk<ProfileRepository>()
    val clearCachedData = ClearCachedData(repository)

    Given("should call repository when clearCachedData() called") {
        coJustRun { repository.clearCachedChatList() }
        coJustRun { repository.clearCachedChatMessages() }
        When {
            clearCachedData.invoke()
            Then {
                coVerifySequence {
                    repository.clearCachedChatList()
                    repository.clearCachedChatMessages()
                }
            }
        }
    }

    Given("should throw exception when clearCachedData() called and repository throws exception") {
        val exception = Exception()
        coEvery { repository.clearCachedChatList() } throws exception
        When {
            val actualException = shouldThrow<Exception> {
                clearCachedData.invoke()
            }
            Then {
                coVerifySequence {
                    repository.clearCachedChatList()
                }
                actualException shouldBe exception
            }
        }
    }
})

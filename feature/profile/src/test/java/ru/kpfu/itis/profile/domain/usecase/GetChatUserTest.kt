package ru.kpfu.itis.profile.domain.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.kpfu.itis.coretesting.Then
import ru.kpfu.itis.coretesting.When
import ru.kpfu.itis.profile.domain.repository.ProfileRepository

class GetChatUserTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val repository = mockk<ProfileRepository>()
    val getChatUser = GetChatUser(repository)

    Given("should call repository when GetChatUser() called") {
        coEvery { repository.getUser() } returns chatUser
        When {
            val actual = getChatUser.invoke()
            Then {
                coVerifySequence {
                    repository.getUser()
                }
                actual shouldBe chatUser
            }
        }
    }

    Given("should throw exception when GetChatUser() called and repository throws exception") {
        val exception = Exception()
        coEvery { repository.getUser() } throws exception
        When {
            val actualException = shouldThrow<Exception> {
                getChatUser()
            }
            Then {
                coVerifySequence {
                    repository.getUser()
                }
                actualException shouldBe exception
            }
        }
    }
})

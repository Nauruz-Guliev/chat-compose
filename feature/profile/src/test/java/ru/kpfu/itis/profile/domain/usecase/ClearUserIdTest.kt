package ru.kpfu.itis.profile.domain.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.kpfu.itis.core_testing.Then
import ru.kpfu.itis.core_testing.When
import ru.kpfu.itis.profile.domain.repository.ProfileRepository

class ClearUserIdTest : BehaviorSpec({

    val repository = mockk<ProfileRepository>()
    val clearUserId = ClearUserId(repository)

    Given("should call repository when clear used id called") {
        coJustRun { repository.getUser() }
        When {
            clearUserId.invoke()
            Then {
                coVerifySequence {
                    repository.clearUserId()
                }
            }
        }
    }

    Given("should throw exception when clear used id called and repository throws exception") {
        val exception = Exception()
        coEvery { repository.clearUserId() } throws exception
        When {
            val actualException = shouldThrow<Exception> {
                clearUserId.invoke()
            }
            Then {
                actualException shouldBe exception
            }
        }
    }
})

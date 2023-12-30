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

class UpdateUserTest : BehaviorSpec({

    val repository = mockk<ProfileRepository>()
    val updateUser = UpdateUser(repository)

    Given("should call repository when UpdateUser() called") {
        coJustRun { repository.updateProfile(updateProfileModel) }
        When {
            updateUser.invoke(updateProfileModel)
            Then {
                coVerifySequence {
                    repository.updateProfile(updateProfileModel)
                }
            }
        }
    }

    Given("should throw exception when UpdateUser() called and repository throws exception") {
        val exception = Exception()
        coEvery { repository.updateProfile(updateProfileModel) } throws exception
        When {
            val actualException = shouldThrow<Exception> {
                repository.updateProfile(updateProfileModel)
            }
            Then {
                actualException shouldBe exception
            }
        }
    }
})
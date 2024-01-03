package ru.kpfu.itis.image_picker.domain.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.kpfu.itis.core_testing.Then
import ru.kpfu.itis.core_testing.When
import ru.kpfu.itis.image_picker.domain.repository.ImageRepository

class LoadImageTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val repository = mockk<ImageRepository>()
    val loadImage = LoadImage(repository)

    Given("should call repository when loadImage() invoked") {
        val images = getListOfImageUrls()
        coEvery { repository.loadImages(SEARCH_QUERY) } returns images
        When {
            val actual = loadImage.invoke(SEARCH_QUERY)
            Then {
                coVerifySequence {
                    repository.loadImages(SEARCH_QUERY)
                }
                actual shouldBe images
            }
        }
    }

    Given("should call repository and throw error when loadImage() invoked") {
        val expectedException = Exception()
        coEvery { repository.loadImages(SEARCH_QUERY) } throws expectedException
        When {
            val exception = shouldThrow<Exception> {
                loadImage(SEARCH_QUERY)
            }
            Then {
                coVerifySequence {
                    repository.loadImages(SEARCH_QUERY)
                }
                exception shouldBe expectedException
            }
        }
    }
})

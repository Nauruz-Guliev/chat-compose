package ru.kpfu.itis.image_picker.domain.usecase

import ru.kpfu.itis.image_picker.domain.model.ImageUrlModel
import ru.kpfu.itis.image_picker.domain.repository.ImageRepository
import javax.inject.Inject

class LoadImage @Inject constructor(
    private val repository: ImageRepository
) {

    suspend operator fun invoke(query: String): List<ImageUrlModel> =
        repository.loadImages(query)
}

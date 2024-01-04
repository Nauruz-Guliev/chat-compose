package ru.kpfu.itis.imagepicker.domain.usecase

import ru.kpfu.itis.imagepicker.domain.model.ImageUrlModel
import ru.kpfu.itis.imagepicker.domain.repository.ImageRepository
import javax.inject.Inject

class LoadImage @Inject constructor(
    private val repository: ImageRepository
) {

    suspend operator fun invoke(query: String): List<ImageUrlModel> =
        repository.loadImages(query)
}

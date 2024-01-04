package ru.kpfu.itis.imagepicker.data.repository

import ru.kpfu.itis.imagepicker.data.mapper.mapToImageModel
import ru.kpfu.itis.imagepicker.data.service.ImageService
import ru.kpfu.itis.imagepicker.domain.model.ImageUrlModel
import ru.kpfu.itis.imagepicker.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val service: ImageService
) : ImageRepository {

    override suspend fun loadImages(query: String): List<ImageUrlModel> {
        return service.getImageResponse(query).mapToImageModel()
    }
}

package ru.kpfu.itis.image_picker.data.repository

import ru.kpfu.itis.image_picker.data.mapper.mapToImageModel
import ru.kpfu.itis.image_picker.data.service.ImageService
import ru.kpfu.itis.image_picker.domain.model.ImageUrlModel
import ru.kpfu.itis.image_picker.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val service: ImageService
) : ImageRepository {

    override suspend fun loadImages(query: String): List<ImageUrlModel> {
        return service.getImageResponse(query).mapToImageModel()
    }
}
package ru.kpfu.itis.image_picker.domain.repository

import ru.kpfu.itis.image_picker.domain.model.ImageUrlModel

interface ImageRepository {
    suspend fun loadImages(query: String): List<ImageUrlModel>
}
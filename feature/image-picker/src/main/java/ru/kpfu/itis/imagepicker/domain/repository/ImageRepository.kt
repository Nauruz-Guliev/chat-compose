package ru.kpfu.itis.imagepicker.domain.repository

import ru.kpfu.itis.imagepicker.domain.model.ImageUrlModel

interface ImageRepository {
    suspend fun loadImages(query: String): List<ImageUrlModel>
}

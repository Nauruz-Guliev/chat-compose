package ru.kpfu.itis.image_picker.data.mapper

import ru.kpfu.itis.image_picker.data.model.ImageResponse
import ru.kpfu.itis.image_picker.domain.model.ImageUrlModel


fun ImageResponse.mapToImageModel(): List<ImageUrlModel> {
    return this.results.map { result ->
        ImageUrlModel(
            url = result.urls.full
        )
    }
}
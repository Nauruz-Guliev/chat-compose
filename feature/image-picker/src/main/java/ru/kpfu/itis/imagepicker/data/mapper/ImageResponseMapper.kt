package ru.kpfu.itis.imagepicker.data.mapper

import ru.kpfu.itis.imagepicker.data.model.ImageResponse
import ru.kpfu.itis.imagepicker.domain.model.ImageUrlModel

fun ImageResponse.mapToImageModel(): List<ImageUrlModel> {
    return this.results.map { result ->
        ImageUrlModel(
            url = result.urls.small
        )
    }
}

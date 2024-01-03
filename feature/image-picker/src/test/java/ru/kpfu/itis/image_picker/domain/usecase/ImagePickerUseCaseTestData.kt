package ru.kpfu.itis.image_picker.domain.usecase

import ru.kpfu.itis.image_picker.domain.model.ImageUrlModel

const val SEARCH_QUERY = "query"

fun getListOfImageUrls(): List<ImageUrlModel> {
    return listOf(
        ImageUrlModel(
            url = "cool_image.com",
        ),
        ImageUrlModel(
            url = "yet_another_image.com"
        )
    )
}
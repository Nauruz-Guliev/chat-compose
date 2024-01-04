package ru.kpfu.itis.imagepicker.domain.usecase

import ru.kpfu.itis.imagepicker.domain.model.ImageUrlModel

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

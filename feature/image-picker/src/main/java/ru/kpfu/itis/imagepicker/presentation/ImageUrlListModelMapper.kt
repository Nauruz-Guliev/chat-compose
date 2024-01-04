package ru.kpfu.itis.imagepicker.presentation

import ru.kpfu.itis.imagepicker.domain.model.ImageUrlModel

fun ImageUrlModel.mapToListModel(): ImageUrlListModel {
    return ImageUrlListModel(
        url = this.url,
        isSelected = false
    )
}

fun List<ImageUrlModel>.mapToListModel(): List<ImageUrlListModel> {
    return this.map { it.mapToListModel() }
}

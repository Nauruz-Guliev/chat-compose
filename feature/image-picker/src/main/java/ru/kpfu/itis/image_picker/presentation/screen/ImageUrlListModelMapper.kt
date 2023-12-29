package ru.kpfu.itis.image_picker.presentation.screen

import ru.kpfu.itis.image_picker.domain.model.ImageUrlModel

fun ImageUrlModel.mapToListModel(): ImageUrlListModel {
    return ImageUrlListModel(
        url = this.url,
        isSelected = false
    )
}

fun List<ImageUrlModel>.mapToListModel(): List<ImageUrlListModel> {
    return this.map { it.mapToListModel() }
}
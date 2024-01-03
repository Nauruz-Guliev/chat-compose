package ru.kpfu.itis.image_picker.presentation.screen.image_picker

import ru.kpfu.itis.image_picker.presentation.screen.ImageUrlListModel

data class ImagePickerState (
    val imageList: List<ImageUrlListModel> = emptyList(),
    val selectedImage: ImageUrlListModel? = null,
    val isImageFound: Boolean = true,
    val searchedQuery: String = "",
    val isResultLoading: Boolean = false
)
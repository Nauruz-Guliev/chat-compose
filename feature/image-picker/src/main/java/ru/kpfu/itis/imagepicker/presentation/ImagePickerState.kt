package ru.kpfu.itis.imagepicker.presentation

data class ImagePickerState(
    val imageList: List<ImageUrlListModel> = emptyList(),
    val selectedImage: ImageUrlListModel? = null,
    val isImageFound: Boolean = true,
    val searchedQuery: String = "",
    val isResultLoading: Boolean = false
)

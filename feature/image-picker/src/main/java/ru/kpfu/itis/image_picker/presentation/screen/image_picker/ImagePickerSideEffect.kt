package ru.kpfu.itis.image_picker.presentation.screen.image_picker


sealed interface ImagePickerSideEffect {

    data class ExceptionHappened(val throwable: Throwable?) : ImagePickerSideEffect
    data object ShowLoading : ImagePickerSideEffect
}
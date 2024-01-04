package ru.kpfu.itis.imagepicker.presentation

sealed interface ImagePickerSideEffect {

    data class ExceptionHappened(val throwable: Throwable?) : ImagePickerSideEffect
}

package ru.kpfu.itis.image_picker.presentation.screen.image_picker

import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.core_ui.base.BaseViewModel
import ru.kpfu.itis.image_picker.domain.usecase.LoadImage
import ru.kpfu.itis.image_picker.presentation.screen.ImageUrlListModel
import ru.kpfu.itis.image_picker.presentation.screen.mapToListModel
import javax.inject.Inject

@HiltViewModel
class ImagePickerViewModel @Inject constructor(
    private val loadImage: LoadImage
) : BaseViewModel<ImagePickerState, ImagePickerSideEffect>() {

    override val container: Container<ImagePickerState, ImagePickerSideEffect> =
        container(ImagePickerState())

    fun searchForAnImage(query: String) = intent {
        if (query.isBlank()) return@intent
        postSideEffect(ImagePickerSideEffect.ShowLoading)
        runCatching {
            loadImage.invoke(query)
        }.onFailure { exception ->
            postSideEffect(ImagePickerSideEffect.ExceptionHappened(exception))
        }.onSuccess { imageList ->
            reduce {
                if (imageList.isNotEmpty()) {
                    state.copy(
                        imageList = imageList.mapToListModel(),
                        isImageFound = true
                    )
                } else {
                    state.copy(
                        isImageFound = false,
                        searchedQuery = query
                    )
                }
            }
        }
    }

    fun resetState() = intent {
        reduce {
            state.copy(
                imageList = emptyList(),
                selectedImage = null,
                isImageFound = true,
                searchedQuery = ""
            )
        }
    }

    fun onImageSelected(model: ImageUrlListModel?) = intent {
        val imageList = getListWithSelectedImage(model)
        reduce {
            state.copy(imageList = imageList, selectedImage = model)
        }
    }

    private fun getListWithSelectedImage(model: ImageUrlListModel?): List<ImageUrlListModel> {
        return container.stateFlow.value.imageList.map { image ->
            if (image.url == model?.url) {
                image.copy(isSelected = true)
            } else {
                image.copy(isSelected = false)
            }
        }
    }
}
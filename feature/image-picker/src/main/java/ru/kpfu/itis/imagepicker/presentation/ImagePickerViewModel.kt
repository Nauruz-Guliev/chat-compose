package ru.kpfu.itis.imagepicker.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.coreui.base.BaseViewModel
import ru.kpfu.itis.imagepicker.domain.model.ImageUrlModel
import ru.kpfu.itis.imagepicker.domain.usecase.LoadImage
import javax.inject.Inject

@HiltViewModel
class ImagePickerViewModel @Inject constructor(
    private val loadImage: LoadImage
) : BaseViewModel<ImagePickerState, ImagePickerSideEffect>() {

    override val container: Container<ImagePickerState, ImagePickerSideEffect> =
        container(ImagePickerState())

    fun searchForAnImage(query: String) = intent {
        if (query.isBlank()) return@intent
        toggleLoadingState()
        runCatching {
            loadImage.invoke(query)
        }.onFailure { exception ->
            postSideEffect(ImagePickerSideEffect.ExceptionHappened(exception))
        }.onSuccess { imageList ->
            toggleLoadingState()
            handleImageLoadResult(imageList, query)
        }
    }

    private fun toggleLoadingState() = intent {
        reduce { state.copy(isResultLoading = !state.isResultLoading) }
    }

    private fun handleImageLoadResult(imageList: List<ImageUrlModel>, query: String) = intent {
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

    fun resetState() = intent {
        reduce {
            state.copy(
                imageList = emptyList(),
                selectedImage = null,
                isImageFound = true,
                searchedQuery = "",
                isResultLoading = false
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

package ru.kpfu.itis.image_picker.presentation.screen.image_picker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.kpfu.itis.core_ui.composable.AnimatedDialog
import ru.kpfu.itis.core_ui.composable.ErrorText
import ru.kpfu.itis.core_ui.composable.HeaderText
import ru.kpfu.itis.core_ui.extension.useDebounce
import ru.kpfu.itis.image_picker.presentation.screen.ImageUrlListModel
import ru.kpfu.itis.core.R as CoreR

private const val TEXT_FIELD_DEBOUNCE_TIME_MILLIS = 800L

@Composable
fun ImagePickerDialog(
    isShown: Boolean,
    onDismissRequest: () -> Unit,
    onImagePicked: (imageUrl: String?) -> Unit,
    viewModel: ImagePickerViewModel = hiltViewModel()
) {
    var imageQuery by rememberSaveable { mutableStateOf("") }
    var errorText by rememberSaveable { mutableStateOf<String?>("") }

    AnimatedDialog(showDialog = isShown, onDismissRequest = onDismissRequest) {

        viewModel.collectSideEffect { sideEffect: ImagePickerSideEffect ->
            when (sideEffect) {
                is ImagePickerSideEffect.ExceptionHappened -> {
                    errorText = sideEffect.throwable?.message
                }
            }
        }

        viewModel.collectAsState().also { imagePickerState ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiary)
                    .clip(RoundedCornerShape(16.dp))
                    .padding(8.dp)
                    .wrapContentHeight(),
            ) {

                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.tertiary)
                        .wrapContentSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    imageQuery.useDebounce(TEXT_FIELD_DEBOUNCE_TIME_MILLIS) { query ->
                        viewModel.searchForAnImage(query)
                    }

                    HeaderText(
                        text = stringResource(id = CoreR.string.picker_header),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(PaddingValues(vertical = 20.dp))
                    )

                    TextField(
                        value = imageQuery,
                        onValueChange = { newText ->
                            imageQuery = newText
                        },
                        maxLines = 1,
                        shape = RoundedCornerShape(8.dp),
                        placeholder = {
                            Text(text = stringResource(id = CoreR.string.image_name))
                        }
                    )

                    if (imagePickerState.value.isImageFound) {
                        ImageList(imagePickerState.value.imageList, viewModel)
                    } else if (imagePickerState.value.isResultLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(40.dp)
                                .padding(vertical = 8.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            strokeWidth = 4.dp
                        )
                    } else {
                        ErrorText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            text = stringResource(
                                id = CoreR.string.error_picker_search_result,
                                imagePickerState.value.searchedQuery
                            )
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {

                        Button(
                            onClick = {
                                imageQuery = ""
                                onImagePicked(imagePickerState.value.selectedImage?.url)
                                onDismissRequest()
                            },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = stringResource(id = CoreR.string.pick),
                                fontSize = 16.sp,
                            )
                        }

                        Button(
                            onClick = {
                                imageQuery = ""
                                viewModel.resetState()
                                onDismissRequest()
                            },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = stringResource(id = CoreR.string.dismiss),
                                fontSize = 16.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ImageList(
    images: List<ImageUrlListModel>,
    viewModel: ImagePickerViewModel
) {
    LazyRow {
        items(images) { imageData ->

            Card(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clickable {
                            viewModel.onImageSelected(imageData)
                        }
                        .border(
                            border = BorderStroke(
                                4.dp,
                                color = if (imageData.isSelected) {
                                    MaterialTheme.colorScheme.onPrimary
                                } else {
                                    Color.Transparent
                                }
                            ),
                        ),
                    contentScale = ContentScale.Crop,
                    model = imageData.url,
                    contentDescription = null,
                )
            }
        }
    }
}
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.kpfu.itis.core_ui.composable.AnimatedDialog
import ru.kpfu.itis.core_ui.composable.ErrorText
import ru.kpfu.itis.core_ui.composable.HeaderText
import ru.kpfu.itis.core_ui.composable.TextFieldWithErrorState
import ru.kpfu.itis.core_ui.extension.useDebounce
import ru.kpfu.itis.core_ui.ui.theme.Persimmon
import ru.kpfu.itis.core_ui.ui.theme.SeaGreen
import ru.kpfu.itis.image_picker.presentation.screen.ImageUrlListModel

private const val TEXT_FIELD_DEBOUNCE_TIME_MILLIS = 300L

@Composable
fun ImagePickerScreen(
    isShown: Boolean,
    onDismissRequest: () -> Unit,
    viewModel: ImagePickerViewModel = hiltViewModel()
) {
    var imageQuery by rememberSaveable { mutableStateOf("") }

    AnimatedDialog(showDialog = isShown, onDismissRequest = onDismissRequest) {

        viewModel.collectSideEffect { sideEffect: ImagePickerSideEffect ->
            handleSideEffect(sideEffect)
        }
        viewModel.collectAsState().apply {

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
                        text = "Search for a profile image",
                        fontSize = 20,
                        modifier = Modifier.padding(PaddingValues(vertical = 20.dp))
                    )

                    TextFieldWithErrorState(
                        modifier = Modifier.fillMaxWidth(),
                        value = imageQuery,
                        onValueChange = { imageQuery = it },
                        labelValue = "Image",
                        horizontalPadding = 0
                    )

                    if (!this@apply.value.isImageFound) {
                        ErrorText(text = "No image found for ${this@apply.value.searchedQuery}")
                    } else {
                        ImageList(this@apply.value.imageList, viewModel)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {

                        Button(
                            onClick = {
                                viewModel.returnBackResult()
                                onDismissRequest()
                            },
                            colors = ButtonDefaults.buttonColors(SeaGreen),
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Confirm",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = { onDismissRequest() },
                            colors = ButtonDefaults.buttonColors(Persimmon),
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Dismiss",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun handleSideEffect(sideEffect: ImagePickerSideEffect) {
    when (sideEffect) {
        is ImagePickerSideEffect.ExceptionHappened -> {

        }

        is ImagePickerSideEffect.ShowLoading -> {

        }
    }
}


@Composable
fun ImageList(
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
                                4.dp, color = if (imageData.isSelected) {
                                    SeaGreen
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
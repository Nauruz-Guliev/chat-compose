package ru.kpfu.itis.image_picker.presentation.screen.image_picker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.kpfu.itis.image_picker.presentation.screen.ImageUrlListModel

@Composable
fun ImagePickerScreen(
    viewModel: ImagePickerViewModel = hiltViewModel()
) {

    viewModel.collectSideEffect { sideEffect: ImagePickerSideEffect ->
        handleSideEffect(sideEffect)
    }
    viewModel.collectAsState().apply {

        var imageQuery by rememberSaveable { mutableStateOf("") }

        Dialog(
            onDismissRequest = { }
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {

                Column(
                    modifier = Modifier
                        .wrapContentSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    TextField(
                        value = imageQuery,
                        onValueChange = {
                            viewModel.searchForAnImage(query = it)
                            imageQuery = it
                        },
                        label = { Text(text = "Search for an image") }
                    )

                    ImageList(this@apply.value.imageList, viewModel)

                    Text(
                        text = "This is a dialog with buttons and an image.",
                        modifier = Modifier.padding(16.dp),
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {

                        TextButton(
                            onClick = { },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Dismiss")
                        }

                        TextButton(
                            onClick = {
                                viewModel.returnBackResult()
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Confirm")
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
    LazyColumn {
        items(images) { imageData ->

            val selectedImage: ImageUrlListModel? by remember { mutableStateOf(null) }


            Card(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .clickable { viewModel.onImageSelected(selectedImage) }
                    .padding(8.dp),
                border = BorderStroke(
                    2.dp, color = if (selectedImage != null) {
                        Color.Blue
                    } else {
                        Color.Transparent
                    }
                )
            ) {
                AsyncImage(
                    model = imageData.url,
                    contentDescription = null,
                )
            }
        }
    }
}
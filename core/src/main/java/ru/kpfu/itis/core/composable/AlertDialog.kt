package ru.kpfu.itis.core.composable

import androidx.annotation.DrawableRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import ru.kpfu.itis.core.R

@Composable
fun ErrorAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    @DrawableRes icon: Int,
    confirmButtonText: String = stringResource(id = R.string.close),
    dismissButtonText: String? = null
) {
    var shouldShowDialog by remember { mutableStateOf(true) }
    if(shouldShowDialog) {
        AlertDialog(
            icon = {
                Icon(
                    ImageVector.vectorResource(
                        icon
                    ), contentDescription = "Icon"
                )
            },
            title = {
                Text(text = dialogTitle)
            },
            text = {
                Text(text = dialogText)
            },
            onDismissRequest = {
                shouldShowDialog = false
                onDismissRequest()
            },
            confirmButton = {
                confirmButtonText.let {
                    TextButton(
                        onClick = {
                            shouldShowDialog = false
                            onConfirmation()
                        }
                    ) {
                        Text(it)
                    }
                }
            },
            dismissButton = {
                dismissButtonText?.let {
                    TextButton(
                        onClick = {
                            shouldShowDialog = false
                            onDismissRequest()
                        }
                    ) {
                        Text(it)
                    }
                }
            }
        )
    }
}
package ru.kpfu.itis.core_ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kpfu.itis.core_ui.resource.Resource
import ru.kpfu.itis.core_ui.ui.theme.AliceBlue
import ru.kpfu.itis.core_ui.validation.ValidationResult

@Composable
fun TextFieldWithErrorState(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelValue: String,
    validationResult: ValidationResult<Resource.String>? = null,
    isPassword: Boolean = false,
    isEnabled: Boolean = true,
    horizontalPadding: Int = 24
) {
    TextField(
        enabled = isEnabled,
        value = value,
        onValueChange = onValueChange,
        label = { Text(labelValue) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.onSurface,
            focusedContainerColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLabelColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.DarkGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = AliceBlue,
            disabledTextColor = MaterialTheme.colorScheme.primary,
            disabledPlaceholderColor = Color.Transparent,
            disabledLabelColor = Color.DarkGray
        ),
        shape = RoundedCornerShape(8.dp),
        isError = validationResult is ValidationResult.Failure,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
    if (validationResult is ValidationResult.Failure) {
        Text(
            text = validationResult.stringResource.getStringValue(LocalContext.current),
            style = TextStyle(
                color = Color.Red,
                fontSize = 12.sp
            ),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
    Spacer(Modifier.height(16.dp))
}

@Composable
fun StyledTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelValue: String,
    horizontalPadding: Int = 24
) {
    Column {
        var textState by remember { mutableStateOf(value) }
        Text(
            text = labelValue,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            textAlign = TextAlign.Start,
        )
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding.dp),
            value = textState,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.onSurface,
                focusedContainerColor = MaterialTheme.colorScheme.onSurface,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.DarkGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = onValueChange,
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            trailingIcon = {
                if (textState.isNotEmpty()) {
                    IconButton(onClick = { textState = "" }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}
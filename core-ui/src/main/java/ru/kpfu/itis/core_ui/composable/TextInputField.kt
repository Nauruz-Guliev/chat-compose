package ru.kpfu.itis.core_ui.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kpfu.itis.core_ui.resource.Resource
import ru.kpfu.itis.core_ui.validation.ValidationResult

@Composable
fun TextFieldWithErrorState(
    value: String,
    onValueChange: (String) -> Unit,
    labelValue: String,
    validationResult: ValidationResult<Resource.String>? = null,
    isPassword: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(labelValue) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        isError = validationResult is ValidationResult.Failure,
        visualTransformation = if(isPassword) PasswordVisualTransformation() else VisualTransformation.None
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
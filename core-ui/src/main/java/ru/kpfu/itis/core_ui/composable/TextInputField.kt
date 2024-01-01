package ru.kpfu.itis.core_ui.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelValue: String? = null,
    validationResult: ValidationResult<Resource.String>? = null,
    isPassword: Boolean = false,
    isEnabled: Boolean = true,
    horizontalPaddingInDp: Int = 24,
    verticalPaddingInDp: Int = 0,
    placeholder: (@Composable () -> Unit)? = null
) {
    TextField(
        maxLines = 1,
        placeholder = placeholder,
        enabled = isEnabled,
        value = value,
        onValueChange = onValueChange,
        label = {
            if (labelValue != null) {
                Text(labelValue)
            }
        },
        modifier = modifier
            .padding(
                horizontal = horizontalPaddingInDp.dp,
                vertical = verticalPaddingInDp.dp
            ),
        shape = RoundedCornerShape(8.dp),
        isError = validationResult is ValidationResult.Failure,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
    if (validationResult is ValidationResult.Failure) {
        Text(
            text = validationResult.stringResource.getStringValue(LocalContext.current),
            style = TextStyle(
                fontSize = 12.sp
            ),
            modifier = Modifier.padding(horizontal = horizontalPaddingInDp.dp)
        )
    }
    Spacer(Modifier.height(16.dp))
}
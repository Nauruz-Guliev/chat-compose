package ru.kpfu.itis.core_ui.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderText(
    text: String,
    modifier: Modifier = Modifier,
    fontColor: Color = Color.DarkGray,
    fontSize: Int = 24
) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle(
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold
        ),
        color = fontColor
    )
    Spacer(modifier = Modifier.size(20.dp))
}

@Composable
fun HeaderText(@StringRes res: Int) {
    HeaderText(text = stringResource(res))
}

@Composable
fun ErrorText(
    text: String,
    modifier: Modifier = Modifier,
    fontSizeInSp: Int = 16
) {
    Text(
        text = text,
        color = Color.Red,
        fontSize = fontSizeInSp.sp,
        modifier = modifier.padding(16.dp)
    )
}

@Composable
fun ErrorText(@StringRes res: Int) {
    ErrorText(text = stringResource(res))
}

package ru.kpfu.itis.core_ui.composable

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun HeaderText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 20.sp
) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        ),
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun HeaderText(@StringRes res: Int) {
    HeaderText(text = stringResource(res))
}

@Composable
fun ErrorText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.error,
        fontSize = fontSize,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ErrorText(@StringRes res: Int) {
    ErrorText(text = stringResource(res))
}

@Composable
fun DisappearingText(
    modifier: Modifier = Modifier,
    text: String,
    delayMillis: Long = 5000L
) {
    var visible by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(delayMillis)
        visible = false
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Text(
            modifier = modifier,
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
        )
    }
}
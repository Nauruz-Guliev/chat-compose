package ru.kpfu.itis.chat.presentation.screen.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.kpfu.itis.core_ui.composable.ErrorAlertDialog
import ru.kpfu.itis.core.R as CoreR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    chatId: String,
    viewModel: ChatViewModel = hiltViewModel()
) {
    var error by remember { mutableStateOf(Throwable()) }
    var showAlert by remember { mutableStateOf(false) }

    viewModel.loadMessages(chatId)

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ChatSideEffect.ExceptionHappened -> {
                error = sideEffect.throwable
                showAlert = true
            }
        }
    }

    viewModel.collectAsState().also { chatState ->

        if (chatState.value.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(80.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    strokeWidth = 10.dp
                )
            }
        } else {
            Scaffold(topBar = {
                Row(
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                ) {
                    if (chatState.value.sender?.profileImage != null) {
                        AsyncImage(
                            contentScale = ContentScale.Crop,
                            model = chatState.value.sender?.profileImage,
                            contentDescription = null,
                            modifier = Modifier
                                .size(64.dp)
                                .padding(12.dp)
                                .clip(RoundedCornerShape(32.dp))
                        )
                    } else {
                        Image(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(12.dp)
                                .size(40.dp)
                        )
                    }
                    TopAppBar(
                        title = { chatState.value.sender?.name?.let { Text(it) } },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }) {

                ConstraintLayout(
                    Modifier
                        .wrapContentHeight()
                        .padding(it)
                        .fillMaxSize()
                ) {
                    val (messages, chatBox) = createRefs()
                    val listState = rememberLazyListState()

                    LaunchedEffect(chatState.value.messages.size) {
                        listState.animateScrollToItem(chatState.value.messages.size)
                    }

                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(messages) {
                                top.linkTo(parent.top)
                                bottom.linkTo(chatBox.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                height = Dimension.fillToConstraints
                            },
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(
                            chatState.value.messages
                        ) { item ->
                            ChatItem(item)
                        }
                    }

                    ChatBox(
                        viewModel::onSendClicked,
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(chatBox) {
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                }

                ErrorAlertDialog(
                    onDismissRequest = {
                        showAlert = false
                    },
                    title = error::class.simpleName.toString(),
                    description = error.message,
                    showDialog = showAlert
                )
            }
        }
    }
}

@Composable
private fun ChatItem(model: ChatMessage) {
    if (!model.isMyMessage) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Start)
                    .clip(
                        RoundedCornerShape(
                            topStart = 48f,
                            topEnd = 48f,
                            bottomStart = 0F,
                            bottomEnd = 40f
                        )
                    )
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(16.dp)
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onPrimary,
                    text = model.message ?: ""
                )
            }
            Text(
                text = model.time,
                modifier = Modifier
                    .align(Alignment.Start),
                fontSize = TextUnit(10f, TextUnitType.Sp),
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(
                        RoundedCornerShape(
                            topStart = 48f,
                            topEnd = 48f,
                            bottomStart = 40f,
                            bottomEnd = 8f
                        )
                    )
                    .background(
                        color = MaterialTheme.colorScheme.secondary
                    )
                    .padding(16.dp)
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onPrimary,
                    text = model.message ?: ""
                )
            }
            Text(
                text = model.time,
                fontSize = TextUnit(10f, TextUnitType.Sp),
                modifier = Modifier
                    .align(Alignment.End)
            )
        }

    }
}

@Composable
private fun ChatBox(
    onSendChatClickListener: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var chatBoxValue by remember { mutableStateOf(TextFieldValue("")) }
    Row(modifier = modifier.padding(16.dp)) {
        TextField(
            value = chatBoxValue,
            onValueChange = { newText ->
                chatBoxValue = newText
            },
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(24.dp),
            placeholder = {
                Text(text = stringResource(id = CoreR.string.type_something))
            }
        )
        IconButton(
            onClick = {
                val msg = chatBoxValue.text
                if (msg.isBlank()) return@IconButton
                onSendChatClickListener(chatBoxValue.text)
                chatBoxValue = TextFieldValue("")
            },
            modifier = Modifier
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.primary)
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}

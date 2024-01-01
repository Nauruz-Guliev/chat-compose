package ru.kpfu.itis.chat.presentation.screen.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ChatScreen(
    chatId: String,
    viewModel: ChatViewModel = hiltViewModel()
) {
    var message by rememberSaveable { mutableStateOf("") }

    viewModel.loadMessages(chatId)

    viewModel.collectAsState().also { chatState ->

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Display friend's profile image and name
                        Image(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Friend Name",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Chat messages go here
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 2.dp, start = 8.dp, end = 8.dp)
            ) {
                items(chatState.value.messages) { chat ->
                    ChatItem(chat)
                }
            }

            // Input field and send button

            ChatBox(onSendChatClickListener = viewModel::onSendClicked)
        }
    }
}

@Composable
private fun ChatItem(model: ChatMessage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .align(if (model.isMyMessage) Alignment.End else Alignment.Start)
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (model.isMyMessage) 48f else 0f,
                        bottomEnd = if (model.isMyMessage) 0f else 48f
                    )
                )
                .padding(16.dp)
        ) {
            Text(text = model.message ?: "")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
            shape = RoundedCornerShape(24.dp),
            placeholder = {
                Text(text = "Type something")
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
                contentDescription = "Send",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}

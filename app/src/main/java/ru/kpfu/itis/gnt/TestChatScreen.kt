package ru.kpfu.itis.gnt

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun ChatScreen() {
    val chatMessages = listOf(
        ChatMessage("Alice", "Hi", true),
        ChatMessage("Bob", "Hello!", false),
        ChatMessage("Alice", "How are you?", true),
        ChatMessage("Bob", "I'm good, thanks!", false),
        // Add more messages as needed
    )

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.weight(1f),
            reverseLayout = true
        ) {
            items(chatMessages) { message ->
                ChatMessageItem(message)
            }
        }

        TextInput()
    }
}

@Composable
fun ChatMessageItem(chatMessage: ChatMessage) {
    val horizontalPadding = 16.dp
    val verticalPadding = 8.dp
    val borderRadius = 16.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding, vertical = verticalPadding),
        horizontalArrangement = if (chatMessage.isSender) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            content = {
                Text(
                    text = chatMessage.message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput() {
    var text by remember { mutableStateOf("") }
    val borderColor = MaterialTheme.colorScheme.primary
    val borderRadius = RoundedCornerShape(16.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f), borderRadius)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = borderRadius
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(text = "Type a message...") },
            textStyle = MaterialTheme.typography.displaySmall,
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = borderColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(
                onSend = {
                    // Handle send message action here
                    // e.g., sendMessage(text)
                    text = ""
                }
            )
        )

        IconButton(
            onClick = {
                // Handle send message action here
                // e.g., sendMessage(text)
                text = ""
            }
        ) {
            Icon(Icons.Filled.Send, contentDescription = "Send")
        }
    }
}

data class ChatMessage(val sender: String, val message: String, val isSender: Boolean)

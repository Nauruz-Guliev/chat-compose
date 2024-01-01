package ru.kpfu.itis.chat.presentation.screen.chat

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatScreen(
    chatId: String?
) {
    Toast.makeText(LocalContext.current, chatId ?: "Hi", Toast.LENGTH_SHORT).show()

    Column(
        modifier = Modifier.fillMaxSize()
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
                Text(
                    text = "Chat Title",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Chat messages go here
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            items(listOf("Hi", "Hello")) { chat ->
                ChatItem(chat)
            }
        }

        // Input field and send button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            TextField(
                value = "",
                onValueChange = { /* Update input state */ },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message") }
            )
            IconButton(
                onClick = { /* Handle send action */ }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Send,
                    contentDescription = "Send"
                )
            }
        }
    }
}

@Composable
fun ChatItem(chat: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(8.dp),
            color = if (true) Color.LightGray else Color.Yellow
        ) {
            Text(
                text = chat,
                modifier = Modifier.padding(16.dp),
                color = if (true) Color.Black else Color.DarkGray
            )
        }
    }
}

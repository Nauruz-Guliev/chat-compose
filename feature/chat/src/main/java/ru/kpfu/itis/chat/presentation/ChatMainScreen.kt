package ru.kpfu.itis.chat.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatMainScreen() {
    val chatMessages = listOf(
        ChatMessage("John", "Hello!", true),
        ChatMessage("Jane", "Hi!", false),
        ChatMessage("John", "How are you?", true),
        ChatMessage("Jane", "I'm good, thanks!", false),
    )

    LazyColumn {
        items(chatMessages) { message ->
            ChatMessageItem(message)
        }
    }
}

@Composable
fun ChatMessageItem(message: ChatMessage) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
    ) {
        Surface(
            color = if (message.isSentByMe) Color(0xFF58C3E8) else Color(0xFFE5E5E5),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Column(Modifier.padding(8.dp)) {
                Text(
                    text = message.sender,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = if (message.isSentByMe) Color.White else Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message.content,
                    fontSize = 16.sp,
                    color = if (message.isSentByMe) Color.White else Color.Black
                )
            }
        }
    }
}

data class ChatMessage(val sender: String, val content: String, val isSentByMe: Boolean)
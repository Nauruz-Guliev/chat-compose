package ru.kpfu.itis.chat.presentation.screen.chat_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import org.orbitmvi.orbit.compose.collectAsState
import ru.kpfu.itis.chat.domain.model.ChatListModel
import ru.kpfu.itis.core.R as CoreR

@Composable
fun ChatListScreen(
    viewModel: ChatListViewModel = hiltViewModel()
) {
    viewModel.collectAsState().also { chatListState ->
        LazyColumn {
            items(chatListState.value.chatList) { user ->
                UserListItem(user) {
                    viewModel.onChatClicked(it)
                }
            }
        }
    }
}

@Composable
fun UserListItem(
    listModel: ChatListModel,
    onChatClicked: (chatId: String?) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onChatClicked(listModel.chatId) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            listModel.friend?.profileImage?.let { url ->
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = url,
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(30.dp))
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = listModel.friend?.name ?: stringResource(id = CoreR.string.unknown_user),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

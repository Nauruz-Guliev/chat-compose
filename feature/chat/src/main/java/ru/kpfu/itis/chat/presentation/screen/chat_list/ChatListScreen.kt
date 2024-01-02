package ru.kpfu.itis.chat.presentation.screen.chat_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import org.orbitmvi.orbit.compose.collectAsState
import ru.kpfu.itis.core.R as CoreR

@Composable
fun ChatListScreen(
    viewModel: ChatListViewModel = hiltViewModel()
) {
    viewModel.collectAsState().also { chatListState ->
        if (chatListState.value.chatList.isNotEmpty()) {
            LazyColumn {
                items(chatListState.value.chatList) { user ->
                    UserListItem(user) {
                        viewModel.onChatClicked(it)
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = CoreR.string.emoji_sad_face),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = TextUnit(80f, TextUnitType.Sp),
                )
                Text(
                    text = stringResource(id = CoreR.string.message_no_chats),
                    fontSize = TextUnit(24f, TextUnitType.Sp),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun UserListItem(
    listModel: ChatItem,
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
            if (listModel.friend.profileImage != null) {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = listModel.friend.profileImage,
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(30.dp))
                )
            } else {
                Image(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .size(60.dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = listModel.friend.name ?: stringResource(id = CoreR.string.unknown_user),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

package ru.kpfu.itis.chat.presentation.screen.chat_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.kpfu.itis.core_ui.composable.ErrorAlertDialog
import ru.kpfu.itis.core.R as CoreR

@Composable
fun ChatListScreen(
    viewModel: ChatListViewModel = hiltViewModel()
) {
    var error by remember { mutableStateOf(Throwable()) }
    var showAlert by remember { mutableStateOf(false) }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ChatListSideEffect.ExceptionHappened -> {
                error = sideEffect.throwable
            }
        }
    }

    viewModel.collectAsState().also { chatListState ->
        when (chatListState.value.screenState) {
            ChatListScreenState.LOADING -> {
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
            }

            ChatListScreenState.CHATS_LOADED -> {
                LazyColumn {
                    items(chatListState.value.chatList) { user ->
                        UserListItem(user) {
                            viewModel.onChatClicked(it)
                        }
                    }
                }
            }

            ChatListScreenState.NO_CHATS -> {
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

@Composable
private fun UserListItem(
    listModel: ChatItem,
    onChatClicked: (chatId: String?) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .clickable { onChatClicked(listModel.chatId) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
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
                        .size(60.dp)
                )
            }

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                text = listModel.friend.name
                    ?: stringResource(id = CoreR.string.unknown_user),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    fontSize = TextUnit(12f, TextUnitType.Sp),
                    text = stringResource(id = CoreR.string.last_updated, listModel.lastUpdated),
                )
            }
        }
    }
}


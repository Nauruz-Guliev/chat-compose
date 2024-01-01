package ru.kpfu.itis.user_search.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import org.orbitmvi.orbit.compose.collectAsState
import ru.kpfu.itis.core_ui.extension.useDebounce

@Composable
fun SearchScreen(
    viewModel: UserSearchViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var searchValue by rememberSaveable { mutableStateOf("") }

        searchValue.useDebounce {
            viewModel.findUser(searchValue)
        }

        OutlinedTextField(
            shape = RoundedCornerShape(12.dp),
            value = searchValue,
            onValueChange = { searchValue = it },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            placeholder = {
                Text(text = "Find a user")
            }
        )

        viewModel.collectAsState().let { userSearchState ->
            UserList(userSearchState.value.users, viewModel)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserList(
    users: List<ChatUserSearchModel>,
    viewModel: UserSearchViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(users) { user ->
            Box(modifier = Modifier.animateItemPlacement()) {
                UserItem(user) { model ->
                    model.user.id?.let { id ->
                        viewModel.createChat(id)
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(
    model: ChatUserSearchModel,
    onItemClicked: (ChatUserSearchModel) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            if (!model.isProfileImageValid) {
                Image(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(120.dp)
                )
            } else {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = model.user.profileImage,
                    contentDescription = null,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(120.dp)
                        .clip(RoundedCornerShape(60.dp))
                )
            }

            model.user.name?.let {
                Text(
                    text = it,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            if (!model.isInFriendList) {
                Button(
                    onClick = { onItemClicked(model) },
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {
                    Text(
                        text = "Add to chats",
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Button(
                    enabled = false,
                    onClick = { onItemClicked(model) },
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {
                    Text(
                        text = "User added",
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

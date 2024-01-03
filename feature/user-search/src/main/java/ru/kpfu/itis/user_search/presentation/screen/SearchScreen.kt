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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.kpfu.itis.core_ui.composable.ErrorAlertDialog
import ru.kpfu.itis.core_ui.extension.useDebounce
import ru.kpfu.itis.core.R as CoreR

@Composable
fun SearchScreen(
    viewModel: UserSearchViewModel = hiltViewModel()
) {
    var searchValue by rememberSaveable { mutableStateOf("") }
    var error by remember { mutableStateOf(Throwable()) }
    var showAlert by remember { mutableStateOf(false) }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is UserSearchSideEffect.ExceptionHappened -> {
                error = sideEffect.throwable
                showAlert = true
            }
        }
    }

    viewModel.collectAsState().also { userSearchState ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

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
                    Text(text = stringResource(id = CoreR.string.find_a_user))
                }
            )
            UserList(userSearchState.value.users, viewModel)
        }
    }

    ErrorAlertDialog(
        onDismissRequest = {
            showAlert = false
            searchValue = ""
            viewModel.resetState()
        },
        title = error::class.simpleName,
        description = error.message,
        showDialog = showAlert
    )
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
                    model.user.id.let { id ->
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

            Button(
                enabled = !model.isInFriendList,
                onClick = { onItemClicked(model) },
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                Text(
                    text = if (model.isInFriendList) {
                        stringResource(id = CoreR.string.add_to_chats)
                    } else {
                        stringResource(id = CoreR.string.user_added)
                    },
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

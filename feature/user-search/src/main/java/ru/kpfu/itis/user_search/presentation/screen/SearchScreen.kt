package ru.kpfu.itis.user_search.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import org.orbitmvi.orbit.compose.collectAsState
import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.core_ui.composable.TextFieldWithErrorState
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

        TextFieldWithErrorState(
            value = searchValue,
            onValueChange = {
                searchValue = it
            },
        )

        viewModel.collectAsState().let { userSearchState ->
            Log.d("USER", userSearchState.value.users.toString())
            UserList(userSearchState.value.users)
        }
    }
}

@Composable
fun UserList(users: List<ChatUser>) {
    LazyVerticalGrid(GridCells.Fixed(2)) {
        items(users) { user ->
            UserItem(user)
        }
    }
}

@Composable
fun UserItem(user: ChatUser) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                contentScale = ContentScale.Crop,
                model = user.profileImage,
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(60.dp))
                    .size(120.dp)
            )

            user.name?.let {
                Text(
                    text = it,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Send Message")
            }
        }
    }
}

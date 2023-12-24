package ru.kpfu.itis.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import ru.kpfu.itis.chat_api.ChatDestinations
import ru.kpfu.itis.core.R as CoreR

sealed class MainScreen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {

    data object Chat : MainScreen(ChatDestinations.MAIN_SCREEN.name, CoreR.string.chat, Icons.Filled.MailOutline)
    data object Search : MainScreen(MainScreenDestinations.SEARCH.name, CoreR.string.search, Icons.Filled.Search)
    data object Profile : MainScreen(MainScreenDestinations.PROFILE.name, CoreR.string.profile, Icons.Filled.Person)
}

enum class MainScreenDestinations {
    //   CHAT,
    SEARCH,
    PROFILE
}

val items = listOf(
    MainScreen.Chat,
    MainScreen.Search,
    MainScreen.Profile
)
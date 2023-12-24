package ru.kpfu.itis.navigation

import androidx.annotation.StringRes
import ru.kpfu.itis.chat_api.ChatDestinations
import ru.kpfu.itis.core.R as CoreR

sealed class MainScreen(val route: String, @StringRes val resourceId: Int) {

    data object Chat : MainScreen(ChatDestinations.MAIN_SCREEN.name, CoreR.string.chat)
    data object Search : MainScreen(MainScreenDestinations.SEARCH.name, CoreR.string.search)
    data object Profile : MainScreen(MainScreenDestinations.PROFILE.name, CoreR.string.profile)
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
package ru.kpfu.itis.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.kpfu.itis.authentication.presentation.screen.signin.SignInScreen
import ru.kpfu.itis.authentication.presentation.screen.signup.SignUpScreen
import ru.kpfu.itis.authentication_api.AuthenticationDestinations
import ru.kpfu.itis.chat.presentation.screen.chat.ChatScreen
import ru.kpfu.itis.chat.presentation.screen.chat_list.ChatListScreen
import ru.kpfu.itis.chat_api.CHAT_ID_KEY
import ru.kpfu.itis.chat_api.ChatDestinations
import ru.kpfu.itis.profile.presentation.screen.ProfileScreen
import ru.kpfu.itis.user_search.presentation.screen.SearchScreen


@Composable
fun MainNavHost(
    navController: NavHostController,
    isAuthenticated: Boolean = false
) {
    var isBottomBarVisible by rememberSaveable { (mutableStateOf(true)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    checkBottomBarVisibility(navBackStackEntry?.destination) { isVisible ->
        isBottomBarVisible = isVisible
    }

    Scaffold(
        bottomBar = {
            if (isBottomBarVisible) {
                BottomNavigation(
                    backgroundColor = Color(0xFF487AC5)
                ) {
                    items.forEach { screen ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = screen.route,
                                    tint = if (currentDestination?.route == screen.route)
                                        MaterialTheme.colorScheme.surfaceTint
                                    else MaterialTheme.colorScheme.onBackground
                                )
                            },
                            label = { Text(stringResource(screen.resourceId)) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->

        val startDestination = if (isAuthenticated) {
            NavigationFeatures.CHAT.name
        } else {
            NavigationFeatures.AUTH.name
        }

        NavHost(
            navController,
            startDestination = startDestination,
            Modifier.padding(innerPadding)
        ) {

            navigation(
                route = NavigationFeatures.AUTH.name,
                startDestination = AuthenticationDestinations.SIGNIN.name
            ) {
                composable(AuthenticationDestinations.SIGNUP.name) {
                    SignUpScreen()
                }
                composable(AuthenticationDestinations.SIGNIN.name) {
                    SignInScreen()
                }
            }

            navigation(
                route = NavigationFeatures.CHAT.name,
                startDestination = ChatDestinations.CHAT_LIST_SCREEN.name
            ) {
                composable(MainScreen.Chat.route) { ChatListScreen() }
                composable(MainScreen.Search.route) { SearchScreen() }
                composable(MainScreen.Profile.route) { ProfileScreen() }
                composable(ChatDestinations.CHAT_LIST_SCREEN.name) {
                    ChatListScreen()
                }
                composable(
                    route = ChatDestinations.CHAT_SCREEN.route,
                    arguments = listOf(navArgument(CHAT_ID_KEY) { type = NavType.StringType })
                ) { backStackEntry ->
                    backStackEntry.arguments?.getString(CHAT_ID_KEY)?.let { ChatScreen(it) }
                }
            }
        }
    }
}

private fun checkBottomBarVisibility(
    destination: NavDestination?,
    isBottomBarVisibleCallback: (Boolean) -> Unit
) {
    when (destination?.route) {
        NavigationFeatures.AUTH.name, AuthenticationDestinations.SIGNIN.name, AuthenticationDestinations.SIGNUP.name ->
            isBottomBarVisibleCallback(false)

        else ->
            isBottomBarVisibleCallback(true)

    }
}

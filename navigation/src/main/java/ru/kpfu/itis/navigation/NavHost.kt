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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import ru.kpfu.itis.authentication.presentation.screen.signin.SignInScreen
import ru.kpfu.itis.authentication.presentation.screen.signup.SignUpScreen
import ru.kpfu.itis.authentication_api.AuthenticationDestinations
import ru.kpfu.itis.chat.presentation.screen.ChatListScreen
import ru.kpfu.itis.chat_api.ChatDestinations
import ru.kpfu.itis.profile.presentation.screen.ProfileScreen
import ru.kpfu.itis.user_search.presentation.screen.SearchScreen

@Composable
fun NavigationHost(navController: NavHostController, isAuthenticated: Boolean) {
    val startDestination = if (isAuthenticated) {
        NavigationFeatures.CHAT.name
    } else {
        NavigationFeatures.AUTH.name
    }
    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = NavigationFeatures.AUTH.name,
            startDestination = ChatDestinations.AUTH_SUCCESS.name
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
            startDestination = ChatDestinations.AUTH_SUCCESS.name
        ) {
            composable(ChatDestinations.AUTH_SUCCESS.name) {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(backgroundColor = Color(0xFF487AC5)) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
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
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = MainScreen.Chat.route,
            Modifier.padding(innerPadding)
        ) {
            composable(MainScreen.Chat.route) { ChatListScreen() }
            composable(MainScreen.Search.route) { SearchScreen() }
            composable(MainScreen.Profile.route) { ProfileScreen() }
        }
    }
}

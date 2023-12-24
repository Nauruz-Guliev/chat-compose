package ru.kpfu.itis.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.kpfu.itis.authentication.presentation.screen.signin.SignInScreen
import ru.kpfu.itis.authentication.presentation.screen.signup.SignUpScreen
import ru.kpfu.itis.authentication_api.AuthenticationDestinations
import ru.kpfu.itis.chat.presentation.ChatMainScreen
import ru.kpfu.itis.chat_api.ChatDestinations

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AuthenticationDestinations.SIGNIN.name) {
        composable(AuthenticationDestinations.SIGNUP.name) {
            SignUpScreen()
        }
        composable(AuthenticationDestinations.SIGNIN.name) {
            SignInScreen()
        }
        composable(ChatDestinations.AUTH_SUCCESS.name) {
            MainScreen()
        }
    }
}

@Composable
private fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
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
        NavHost(navController, startDestination = MainScreen.Chat.route, Modifier.padding(innerPadding)) {
            composable(MainScreen.Chat.route) { ChatMainScreen() }
            composable(MainScreen.Search.route) { }
            composable(MainScreen.Profile.route) { }
        }
    }
}

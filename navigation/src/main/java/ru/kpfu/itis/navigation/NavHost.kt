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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.firebase.analytics.FirebaseAnalytics
import ru.kpfu.itis.authentication.presentation.screen.signin.SignInScreen
import ru.kpfu.itis.authentication.presentation.screen.signup.SignUpScreen
import ru.kpfu.itis.authentication_api.AuthenticationDestinations
import ru.kpfu.itis.chat.presentation.screen.chat.ChatScreen
import ru.kpfu.itis.chat.presentation.screen.chat_list.ChatListScreen
import ru.kpfu.itis.chat_api.CHAT_ID_KEY
import ru.kpfu.itis.chat_api.ChatDestinations
import ru.kpfu.itis.core_ui.extension.containsEach
import ru.kpfu.itis.core_ui.ui.theme.md_theme_light_inverseSurface
import ru.kpfu.itis.profile.presentation.screen.ProfileScreen
import ru.kpfu.itis.profile_api.ProfileDestinations
import ru.kpfu.itis.user_search.presentation.screen.SearchScreen
import ru.kpfu.itis.user_search_api.UserSearchDestinations

@Composable
fun MainNavHost(
    navController: NavHostController,
    isAuthenticated: Boolean = false,
    analytics: FirebaseAnalytics
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
                    backgroundColor = MaterialTheme.colorScheme.primary
                ) {
                    items.forEach { screen ->
                        if (isItemSelected(currentDestination?.route, screen)) {
                            BottomNavigationItem(
                                icon = {
                                    Icon(
                                        imageVector = screen.icon,
                                        contentDescription = screen.route,
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                },
                                label = {
                                    Text(
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        text = stringResource(screen.resourceId)
                                    )
                                },
                                selected = true,
                                onClick = { onBottomBarItemClicked(navController, screen) }
                            )
                        } else {
                            BottomNavigationItem(
                                icon = {
                                    Icon(
                                        imageVector = screen.icon,
                                        contentDescription = screen.route,
                                        tint = md_theme_light_inverseSurface
                                    )
                                },
                                label = {
                                    Text(
                                        color = md_theme_light_inverseSurface,
                                        text = stringResource(screen.resourceId)
                                    )
                                },
                                selected = false,
                                onClick = { onBottomBarItemClicked(navController, screen) }
                            )
                        }
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
                startDestination = MainScreen.Chat.route
            ) {
                trackAuthSuccessEvent(analytics)
                navigation(
                    route = MainScreen.Chat.route,
                    startDestination = ChatDestinations.CHAT_LIST_SCREEN.name
                ) {
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
                navigation(
                    route = MainScreen.Search.route,
                    startDestination = UserSearchDestinations.SEARCH_SCREEN.name,
                ) {
                    composable(UserSearchDestinations.SEARCH_SCREEN.name) {
                        SearchScreen()
                    }
                }
                navigation(
                    route = MainScreen.Profile.route,
                    startDestination = ProfileDestinations.PROFILE_SCREEN.name,
                ) {
                    composable(ProfileDestinations.PROFILE_SCREEN.name) {
                        ProfileScreen()
                    }
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

private fun onBottomBarItemClicked(navController: NavHostController, screen: MainScreen) {
    navController.navigate(screen.route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun isItemSelected(currentRoute: String?, availableRoute: MainScreen): Boolean {
    return when {
        listOf(
            MainScreen.Chat.route, ChatDestinations.CHAT_LIST_SCREEN.name,
            ChatDestinations.CHAT_SCREEN.route
        ).containsEach(
            currentRoute, availableRoute.route
        ) || listOf(
            MainScreen.Search.route,
            UserSearchDestinations.SEARCH_SCREEN.name
        ).containsEach(
            currentRoute, availableRoute.route
        ) || listOf(
            MainScreen.Profile.route,
            ProfileDestinations.PROFILE_SCREEN.name
        ).containsEach(
            currentRoute, availableRoute.route
        ) -> true

        else -> false
    }
}

private fun trackAuthSuccessEvent(analytics: FirebaseAnalytics) {
    analytics.logEvent(FirebaseAnalytics.Event.LOGIN, null)
}

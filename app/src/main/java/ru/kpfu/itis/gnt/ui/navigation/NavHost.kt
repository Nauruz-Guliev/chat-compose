package ru.kpfu.itis.gnt.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.kpfu.itis.authentication.presentation.screen.signup.SignUpScreen

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationDestinations.LOGIN.name) {
        composable(NavigationDestinations.LOGIN.name) {
            SignUpScreen(navController)
        }
    }
}

package ru.kpfu.itis.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.kpfu.itis.authentication.presentation.screen.signin.SignInScreen
import ru.kpfu.itis.authentication.presentation.screen.signup.SignUpScreen
import ru.kpfu.itis.authentication_api.AuthenticationDestinations

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AuthenticationDestinations.SIGNIN.name) {
        composable(AuthenticationDestinations.SIGNUP.name) {
            SignUpScreen()
        }
        composable(AuthenticationDestinations.SIGNIN.name) {
            SignInScreen()
        }
    }
}

package ru.kpfu.itis.gnt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import dagger.hilt.android.AndroidEntryPoint
import ru.kpfu.itis.gnt.ui.theme.ChatcomposeTheme
import ru.kpfu.itis.navigation.NavigationHost
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatcomposeTheme {
                NavigationHost(navController = navHostController)
            }
        }
    }
}

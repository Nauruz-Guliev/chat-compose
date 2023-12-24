package ru.kpfu.itis.gnt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.kpfu.itis.core_data.UserStore
import ru.kpfu.itis.gnt.ui.theme.ChatcomposeTheme
import ru.kpfu.itis.navigation.NavigationHost
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var navHostController: NavHostController
    @Inject lateinit var userStore: UserStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.Main) {
            val userId = lifecycleScope.async(Dispatchers.IO) { userStore.getUserId() }.await()
            setContent {
                ChatcomposeTheme {
                    NavigationHost(navController = navHostController, isAuthenticated = userId != null)
                }
            }
        }
    }
}

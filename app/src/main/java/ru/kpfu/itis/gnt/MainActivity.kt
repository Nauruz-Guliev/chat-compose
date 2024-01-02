package ru.kpfu.itis.gnt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.kpfu.itis.core_data.UserStore
import ru.kpfu.itis.core_ui.ui.theme.ChatcomposeTheme
import ru.kpfu.itis.navigation.MainNavHost
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var navHostController: NavHostController
    @Inject
    lateinit var userStore: UserStore
    @Inject
    lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.Main) {
            trackAppOpenedEvent(analytics)
            val userId = lifecycleScope.async(Dispatchers.IO) { userStore.getUserId() }.await()
            setContent {
                ChatcomposeTheme {
                    MainNavHost(
                        analytics = analytics,
                        navController = navHostController,
                        isAuthenticated = userId != null
                    )
                }
            }
        }
    }

    private fun trackAppOpenedEvent(analytics: FirebaseAnalytics) {
        analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)
    }
}


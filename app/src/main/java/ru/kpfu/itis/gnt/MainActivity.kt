package ru.kpfu.itis.gnt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.kpfu.itis.coredata.UserStore
import ru.kpfu.itis.coredata.di.IoDispatcher
import ru.kpfu.itis.coredata.di.MainDispatcher
import ru.kpfu.itis.coreui.ui.theme.ChatcomposeTheme
import ru.kpfu.itis.navigation.MainNavHost
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navHostController: NavHostController
    @Inject
    lateinit var userStore: UserStore
    @Inject
    lateinit var analytics: FirebaseAnalytics
    @Inject
    @IoDispatcher
    lateinit var ioDispatcher: CoroutineDispatcher
    @Inject
    @MainDispatcher
    lateinit var mainDispatcher: CoroutineDispatcher
    private var permissionManager: PermissionManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionManager = PermissionManager(this)
        lifecycleScope.launch(mainDispatcher) {
            trackAppOpenedEvent(analytics)
            val userId = lifecycleScope.async(ioDispatcher) { userStore.getUserId() }.await()
            setContent {
                ChatcomposeTheme {
                    permissionManager?.setUpNotificationPermissions()
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

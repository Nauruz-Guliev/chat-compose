package ru.kpfu.itis.gnt

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import ru.kpfu.itis.coreui.composable.ErrorAlertDialog
import ru.kpfu.itis.core.R as CoreR

class PermissionManager(
    private val activity: ComponentActivity
) {

    private val isPermissionNotGrantedState = MutableStateFlow<Boolean?>(null)
    private val permissionRequest = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isPermissionGranted: Boolean ->
        if (!isPermissionGranted) {
            handleNotificationsPermission()
        }
    }

    private fun handleNotificationsPermission() {
        when {
            ContextCompat.checkSelfPermission(
                activity,
                POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED -> {
                isPermissionNotGrantedState.tryEmit(false)
            }

            shouldShowRequestPermissionRationale(activity, POST_NOTIFICATIONS) -> {
                isPermissionNotGrantedState.tryEmit(true)
            }

            else -> {
                permissionRequest.launch(POST_NOTIFICATIONS)
            }
        }
    }

    @Composable
    fun setUpNotificationPermissions() {
        handleNotificationsPermission()
        ErrorAlertDialog(
            onDismissRequest = {
                isPermissionNotGrantedState.tryEmit(false)
            },
            onButtonClicked = {
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    data = Uri.fromParts("package", activity.packageName, null)
                }.also { intent ->
                    activity.startActivity(intent)
                }
            },
            title = stringResource(id = CoreR.string.error_permission),
            description = stringResource(id = CoreR.string.error_permission_not_granted_message),
            showDialog = isPermissionNotGrantedState.collectAsState().value ?: false,
            buttonTitle = stringResource(id = CoreR.string.open_settings)
        )
    }
}

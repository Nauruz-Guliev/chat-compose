package ru.kpfu.itis.profile.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.core_ui.composable.DisappearingText
import ru.kpfu.itis.core_ui.composable.ErrorAlertDialog
import ru.kpfu.itis.core_ui.composable.TextFieldWithErrorState
import ru.kpfu.itis.core_ui.ui.theme.AliceBlue
import ru.kpfu.itis.core_ui.ui.theme.Persimmon
import ru.kpfu.itis.core_ui.ui.theme.SeaGreen
import ru.kpfu.itis.image_picker.presentation.screen.image_picker.ImagePickerScreen
import ru.kpfu.itis.core.R as CoreR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {

    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var isEditing by rememberSaveable { mutableStateOf(false) }

    HandleSideEffects(
        viewModel = viewModel,
        resetFieldsAction = {
            email = ""
            name = ""
            viewModel.loadUser()
        },
        setupProfileInitialValuesAction = { chatUser: ChatUser? ->
            name = chatUser?.name ?: name
            email = chatUser?.email ?: email
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {

        viewModel.collectAsState().apply {

            var showImagePickerDialog by remember { mutableStateOf(false) }
            var selectedImageUrl by remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
            ) {
                Card(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(60.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    onClick = {
                        showImagePickerDialog = true
                    }
                ) {
                    if (selectedImageUrl.isBlank()) {
                        Image(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(AliceBlue),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(120.dp)
                        )
                    }
                }
                DisappearingText(
                    text = stringResource(id = CoreR.string.change_profile_image_text),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp)
                )
            }

            TextFieldWithErrorState(
                isEnabled = isEditing,
                value = name,
                onValueChange = { name = it },
                labelValue = stringResource(id = CoreR.string.name),
                validationResult = this.value.nameValidationResult,
            )

            TextFieldWithErrorState(
                isEnabled = isEditing,
                value = email,
                onValueChange = { email = it },
                labelValue = stringResource(id = CoreR.string.email),
                validationResult = this.value.emailValidationResult,
            )

            Button(
                onClick = {
                    if (isEditing) viewModel.apply {
                        updateProfile(name, email)
                        loadUser()
                    }
                    isEditing = !isEditing
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                colors = if (isEditing) ButtonDefaults.buttonColors(SeaGreen)
                else ButtonDefaults.buttonColors()
            ) {
                Text(
                    text = if (isEditing)
                        stringResource(id = CoreR.string.save_profile)
                    else stringResource(id = CoreR.string.edit_mode)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = { viewModel.exitProfile() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 20.dp, bottom = 20.dp),
                colors = ButtonDefaults.buttonColors(Persimmon)
            ) {
                Text(text = stringResource(id = CoreR.string.exit_account))
            }

            ImagePickerScreen(
                isShown = showImagePickerDialog,
                onDismissRequest = {
                    showImagePickerDialog = false
                }
            )
        }
    }
}

@Composable
fun HandleSideEffects(
    viewModel: ProfileViewModel,
    resetFieldsAction: () -> Unit,
    setupProfileInitialValuesAction: (ChatUser?) -> Unit
) {
    var alert by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<Throwable?>(null) }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ProfileSideEffect.ExceptionHappened -> {
                error = sideEffect.throwable
                alert = true
            }

            is ProfileSideEffect.ValidationFailure -> {
                error = Exception("Shiiiit")
                alert = true
            }

            is ProfileSideEffect.UserLoaded -> {
                setupProfileInitialValuesAction(sideEffect.user)
            }
        }
    }

    if (alert) {
        ErrorAlertDialog(
            onConfirmation = resetFieldsAction,
            onDismissRequest = resetFieldsAction,
            dialogTitle = (error ?: Exception())::class.simpleName.toString(),
            dialogText = error?.message ?: stringResource(id = CoreR.string.error_unknown),
            icon = CoreR.drawable.baseline_error_24,
        )
    }
}
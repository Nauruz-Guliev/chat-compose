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
import coil.compose.AsyncImage
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.core_ui.composable.DisappearingText
import ru.kpfu.itis.core_ui.composable.ErrorAlertDialog
import ru.kpfu.itis.core_ui.composable.ErrorText
import ru.kpfu.itis.core_ui.composable.TextFieldWithErrorState
import ru.kpfu.itis.core_ui.ui.theme.AliceBlue
import ru.kpfu.itis.core_ui.ui.theme.Persimmon
import ru.kpfu.itis.core_ui.ui.theme.SeaGreen
import ru.kpfu.itis.image_picker.presentation.screen.image_picker.ImagePickerDialog
import ru.kpfu.itis.core.R as CoreR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordRepeat by rememberSaveable { mutableStateOf("") }
    var pickedProfileImage by rememberSaveable { mutableStateOf<String?>(null) }
    var isEditing by rememberSaveable { mutableStateOf(false) }
    var showImagePickerDialog by remember { mutableStateOf(false) }
    var showErrorAlert by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<Throwable?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {

        viewModel.collectSideEffect { sideEffect ->
            handleSideEffect(
                sideEffect = sideEffect,
                showAlertAction = { shouldShowAlert ->
                    showErrorAlert = shouldShowAlert
                },
                errorAction = { errorValue ->
                    error = errorValue
                },
                setupProfileInitialValuesAction = { chatUser ->
                    name = chatUser?.name ?: name
                    email = chatUser?.email ?: email
                }
            )
        }

        viewModel.collectAsState().also { profileState ->
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
                        if (isEditing) {
                            showImagePickerDialog = true
                        }
                    },
                    enabled = isEditing
                ) {
                    if (pickedProfileImage.isNullOrBlank() && profileState.value.user?.profileImage.isNullOrBlank()) {
                        Image(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(AliceBlue),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(120.dp)
                        )
                    } else if (!pickedProfileImage.isNullOrBlank()) {
                        AsyncImage(
                            contentScale = ContentScale.Crop,
                            model = pickedProfileImage,
                            contentDescription = null,
                            modifier = Modifier
                                .size(120.dp)
                        )
                    } else {
                        AsyncImage(
                            contentScale = ContentScale.Crop,
                            model = profileState.value.user?.profileImage,
                            contentDescription = null,
                            modifier = Modifier
                                .size(120.dp)
                        )
                    }
                }
                if (isEditing) {
                    DisappearingText(
                        text = stringResource(id = CoreR.string.change_profile_image_text),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 8.dp)
                    )
                }
            }

            TextFieldWithErrorState(
                isEnabled = isEditing,
                value = name,
                onValueChange = { name = it },
                labelValue = stringResource(id = CoreR.string.name),
                validationResult = profileState.value.nameValidationResult,
            )

            TextFieldWithErrorState(
                isEnabled = isEditing,
                value = email,
                onValueChange = { email = it },
                labelValue = stringResource(id = CoreR.string.email),
                validationResult = profileState.value.emailValidationResult,
            )

            TextFieldWithErrorState(
                isPassword = true,
                isEnabled = isEditing,
                value = password,
                onValueChange = { password = it },
                labelValue = stringResource(id = CoreR.string.password),
                validationResult = profileState.value.passwordValidationResult,
            )

            TextFieldWithErrorState(
                isPassword = true,
                isEnabled = isEditing,
                value = passwordRepeat,
                onValueChange = { passwordRepeat = it },
                labelValue = stringResource(id = CoreR.string.password_repeat),
                validationResult = profileState.value.passwordRepeatValidationResult,
            )

            if(!profileState.value.isValidationSuccessful) {
                ErrorText(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(id = CoreR.string.error_profile_validation_error)
                )
            }

            Button(
                onClick = {
                    if (isEditing) viewModel.run {
                        updateProfile(
                            name = name,
                            email = email,
                            imageUrl = pickedProfileImage
                                ?: profileState.value.user?.profileImage,
                            password = password,
                            passwordRepeat = passwordRepeat
                        )
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

            ImagePickerDialog(
                isShown = showImagePickerDialog,
                onDismissRequest = {
                    showImagePickerDialog = false
                },
                onImagePicked = { imageUrl ->
                    pickedProfileImage = imageUrl
                }
            )

            ErrorAlertDialog(
                title = (error ?: Exception())::class.simpleName.toString(),
                description = error?.message
                    ?: stringResource(id = CoreR.string.error_unknown),
                onDismissRequest = {
                    showErrorAlert = false
                    email = ""
                    name = ""
                    viewModel.loadUser()
                },
                showDialog = showErrorAlert
            )
        }
    }
}

fun handleSideEffect(
    sideEffect: ProfileSideEffect,
    showAlertAction: (Boolean) -> Unit,
    errorAction: (Throwable?) -> Unit,
    setupProfileInitialValuesAction: (ChatUser?) -> Unit
) {
    when (sideEffect) {
        is ProfileSideEffect.ExceptionHappened -> {
            errorAction(sideEffect.throwable)
            showAlertAction(true)
        }

        is ProfileSideEffect.UserLoaded -> {
            setupProfileInitialValuesAction(sideEffect.user)
        }
    }
}
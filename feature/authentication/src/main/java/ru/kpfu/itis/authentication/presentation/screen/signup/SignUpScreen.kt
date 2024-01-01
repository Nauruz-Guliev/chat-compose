package ru.kpfu.itis.authentication.presentation.screen.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.kpfu.itis.core_ui.composable.ErrorAlertDialog
import ru.kpfu.itis.core_ui.composable.HeaderText
import ru.kpfu.itis.core_ui.composable.ProgressButton
import ru.kpfu.itis.core_ui.composable.TextFieldWithErrorState
import ru.kpfu.itis.core.R as CoreR

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    var showSignUpButton by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    HandleSideEffects(
        viewModel = viewModel,
        isLoading = { isLoading = it },
        resetFieldsAction = {
            name = ""
            email = ""
            passwordRepeat = ""
            password = ""
            viewModel.resetState()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeaderText(CoreR.string.create_account)

        viewModel.collectAsState().apply {

            TextFieldWithErrorState(
                value = name,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { name = it },
                labelValue = stringResource(id = CoreR.string.name),
                validationResult = this.value.nameValidationResult
            )

            TextFieldWithErrorState(
                value = email,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { email = it },
                labelValue = stringResource(id = CoreR.string.email),
                validationResult = this.value.emailValidationResult
            )

            TextFieldWithErrorState(
                value = password,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { password = it },
                labelValue = stringResource(id = CoreR.string.password),
                validationResult = this.value.passwordValidationResult,
                isPassword = true
            )

            TextFieldWithErrorState(
                value = passwordRepeat,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { passwordRepeat = it },
                labelValue = stringResource(id = CoreR.string.password_repeat),
                validationResult = this.value.passwordRepeatValidationResult,
                isPassword = true
            )
        }

        Spacer(Modifier.height(16.dp))

        ProgressButton(
            text = stringResource(id = CoreR.string.signup),
            isLoading = isLoading
        ) {
            viewModel.signUp(name, email, password, passwordRepeat)
        }

        TextButton(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                viewModel.navigateSignIn()
            }
        ) {
            Text(text = stringResource(id = CoreR.string.message_sign_in))
        }

        LaunchedEffect(Unit) {
            showSignUpButton = true
        }
    }
}

@Composable
fun HandleSideEffects(
    viewModel: SignUpViewModel,
    isLoading: (Boolean) -> Unit,
    resetFieldsAction: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<Throwable?>(null) }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SignUpSideEffect.ExceptionHappened -> {
                isLoading(false)
                error = sideEffect.throwable
                showDialog = true
            }
            is SignUpSideEffect.ShowLoading -> {
                isLoading(true)
            }
            is SignUpSideEffect.ValidationFailure -> {
                isLoading(false)
            }
        }
    }

    if (showDialog) {
        ErrorAlertDialog(
            onDismissRequest = {
                showDialog = false
                resetFieldsAction()
            },
            title = (error ?: Exception())::class.simpleName.toString(),
            description = error?.message ?: stringResource(id = CoreR.string.error_unknown),
            showDialog = showDialog
        )
    }
}

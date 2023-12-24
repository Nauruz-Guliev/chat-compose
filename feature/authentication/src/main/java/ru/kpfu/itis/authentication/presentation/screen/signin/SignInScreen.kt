package ru.kpfu.itis.authentication.presentation.screen.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import ru.kpfu.itis.core.composable.ErrorAlertDialog
import ru.kpfu.itis.core.composable.HeaderText
import ru.kpfu.itis.core.composable.ProgressButton
import ru.kpfu.itis.core.composable.TextFieldWithErrorState
import ru.kpfu.itis.core.R as CoreR

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel()
) {

    var showSignInButton by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    HandleSideEffects(
        viewModel = viewModel,
        isLoading = { isLoading = it },
        resetFieldsAction = {
            email = ""
            password = ""
            viewModel.resetState()
        }
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HeaderText(text = stringResource(id = CoreR.string.welcome_back))

            Spacer(modifier = Modifier.height(16.dp))

            viewModel.collectAsState().apply {

                TextFieldWithErrorState(
                    value = email,
                    onValueChange = { email = it },
                    labelValue = stringResource(id = CoreR.string.email),
                    validationResult = this.value.emailValidationResult,
                )

                TextFieldWithErrorState(
                    value = password,
                    onValueChange = { password = it },
                    labelValue = stringResource(id = CoreR.string.password),
                    validationResult = this.value.passwordValidationResult,
                    isPassword = true
                )
            }

            ProgressButton(
                text = stringResource(id = CoreR.string.signin),
                isLoading = isLoading
            ) {
                viewModel.signIn(email, password)
            }

            TextButton(
                modifier = Modifier.padding(top = 16.dp),
                onClick = {
                    viewModel.navigateSignUp()
                }
            ) {
                Text(text = stringResource(id = CoreR.string.message_sign_up))
            }

            LaunchedEffect(Unit) {
                showSignInButton = true
            }
        }
    }
}

@Composable
fun HandleSideEffects(
    viewModel: SignInViewModel,
    isLoading: (Boolean) -> Unit,
    resetFieldsAction: () -> Unit
) {
    var alert by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<Throwable?>(null) }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SignInSideEffect.ExceptionHappened -> {
                isLoading(false)
                error = sideEffect.throwable
                alert = true
            }
            is SignInSideEffect.ShowLoading -> {
                isLoading(true)
            }
            is SignInSideEffect.ValidationFailure -> {
                isLoading(false)
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
package ru.kpfu.itis.authentication.presentation.screen.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import ru.kpfu.itis.coreui.composable.ErrorAlertDialog
import ru.kpfu.itis.coreui.composable.HeaderText
import ru.kpfu.itis.coreui.composable.ProgressButton
import ru.kpfu.itis.coreui.composable.TextFieldWithErrorState
import ru.kpfu.itis.core.R as CoreR

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<Throwable?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SignInSideEffect.ExceptionHappened -> {
                isLoading = false
                error = sideEffect.throwable
                showDialog = true
            }
        }
    }

    viewModel.collectAsState().also { signInState ->
        isLoading = signInState.value.isLoading

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                HeaderText(text = stringResource(id = CoreR.string.welcome_back))

                Spacer(modifier = Modifier.height(16.dp))

                TextFieldWithErrorState(
                    value = email,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { email = it },
                    labelValue = stringResource(id = CoreR.string.email),
                    validationResult = signInState.value.emailValidationResult,
                )

                TextFieldWithErrorState(
                    value = password,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { password = it },
                    labelValue = stringResource(id = CoreR.string.password),
                    validationResult = signInState.value.passwordValidationResult,
                    isPassword = true
                )

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
            }
        }

        ErrorAlertDialog(
            onDismissRequest = {
                showDialog = false
                viewModel.resetState()
            },
            title = error?.let { exception ->
                exception::class.java.simpleName
            } ?: stringResource(id = CoreR.string.error),
            description = error?.cause?.message,
            showDialog = showDialog
        )
    }
}

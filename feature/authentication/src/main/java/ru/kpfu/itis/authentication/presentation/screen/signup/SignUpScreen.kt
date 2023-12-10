package ru.kpfu.itis.authentication.presentation.screen.signup

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SignUpScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: SignUpViewModel = hiltViewModel()
) {

    var showSignUpButton by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    HandleState(viewModel = viewModel) { isLoading = it }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
        Spacer(Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
        Spacer(Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
        Spacer(Modifier.height(16.dp))

        TextField(
            value = passwordRepeat,
            onValueChange = { passwordRepeat = it },
            label = { Text("Repeat Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
        Spacer(Modifier.height(32.dp))

        ProgressButton(
            text = "SignUp",
            isLoading = isLoading
        ) {
            viewModel.signUp(name, email, password)
        }
        TextButton(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                viewModel.navigateToSignIn(navController)
            }
        ) {
            Text(text = "Already have an account? Sign In")
        }
    }

    LaunchedEffect(Unit) {
        showSignUpButton = true
    }
}

@Composable
fun HandleState(viewModel: SignUpViewModel, isLoading: (Boolean) -> Unit) {
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SignUpSideEffect.ExceptionHappened -> {
                isLoading(false)
                Log.e("ERROR", sideEffect.throwable?.message.toString())
            }

            is SignUpSideEffect.NavigateToMainFragment -> {
                isLoading(false)
                Log.e("ERROR", "Not navigatable yet")
            }

            is SignUpSideEffect.ShowLoading -> {
                Log.e("ERROR", "LOADING")
                isLoading(true)
            }

            is SignUpSideEffect.NavigateBack -> {
                isLoading(false)
                Log.e("ERROR", "Not navigatable yet")
            }

            is SignUpSideEffect.NavigateToLogin -> {
                isLoading(false)
                Log.e("ERROR", "Not navigatable yet")
            }
        }
    }
}

@Composable
fun ProgressButton(
    text: String,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.wrapContentSize()
    ) {
        Button(
            onClick = onClick,
            enabled = !isLoading,
            modifier = Modifier
                .wrapContentWidth()
                .padding(16.dp)
        ) {
            Text(text = text)
        }

        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn() + expandIn(),
            exit = fadeOut() + shrinkOut()
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(24.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 2.dp
            )
        }
    }
}
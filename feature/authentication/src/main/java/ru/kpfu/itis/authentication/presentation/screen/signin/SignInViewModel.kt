package ru.kpfu.itis.authentication.presentation.screen.signin

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.authentication.domain.usecase.SignIn
import ru.kpfu.itis.authentication.presentation.validator.EmailValidator
import ru.kpfu.itis.authentication.presentation.validator.PasswordValidator
import ru.kpfu.itis.authentication_api.AuthenticationDestinations
import ru.kpfu.itis.chat_api.ChatDestinations
import ru.kpfu.itis.core.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signIn: SignIn,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
    private val navController: NavHostController
) : BaseViewModel<SignInState, SignInSideEffect>() {

    override val container: Container<SignInState, SignInSideEffect> =
        container(SignInState())

    fun signIn(email: String, password: String) = intent {
        postSideEffect(SignInSideEffect.ShowLoading)
        validate(email, password)
        try {
            signIn.invoke(email, password)
            navigateToMainScreen()
        } catch (ex: Exception) {
            postSideEffect(SignInSideEffect.ExceptionHappened(ex))
        }
    }

    fun resetState() = intent {
        reduce {
            state.copy(
                emailValidationResult = null,
                passwordValidationResult = null,
            )
        }
    }

    fun navigateSignUp() {
        navController.navigate(AuthenticationDestinations.SIGNUP.name)
    }

    private fun validate(email: String, password: String) = intent {
        reduce {
            state.copy(
                emailValidationResult = emailValidator(email),
                passwordValidationResult = passwordValidator(password)
            )
        }
    }

    private fun navigateToMainScreen() {
        viewModelScope.launch(Dispatchers.Main) {
            navController.navigate(ChatDestinations.AUTH_SUCCESS.name) {
                popUpTo(0)
            }
        }
    }
}
package ru.kpfu.itis.authentication.presentation.screen.signin

import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.authentication.domain.model.User
import ru.kpfu.itis.authentication.domain.usecase.SignIn
import ru.kpfu.itis.authentication_api.AuthenticationDestinations
import ru.kpfu.itis.chat_api.ChatDestinations
import ru.kpfu.itis.coreui.base.BaseViewModel
import ru.kpfu.itis.coreui.validation.EmailValidator
import ru.kpfu.itis.coreui.validation.PasswordValidator
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
        reduce { state.copy(isLoading = true) }
        validate(email, password).invokeOnCompletion {
            if (isValidationSuccessful(state)) {
                handleSignIn(email, password)
            } else {
                intent { reduce { state.copy(isLoading = false) } }
            }
        }
    }

    private fun handleSignIn(email: String, password: String) = intent {
        runCatching {
            val user = User(email = email, password = password)
            signIn.invoke(user)
        }.onSuccess {
            navigateToMainScreen()
        }.onFailure { exception ->
            reduce { state.copy(isLoading = false) }
            postSideEffect(SignInSideEffect.ExceptionHappened(exception))
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

    fun navigateSignUp() = intent {
        navController.navigateSavingBackStack(AuthenticationDestinations.SIGNUP_SCREEN.name)
    }

    private fun validate(email: String, password: String) = intent {
        reduce {
            state.copy(
                emailValidationResult = emailValidator(email),
                passwordValidationResult = passwordValidator(password)
            )
        }
    }

    private fun navigateToMainScreen() = intent {
        navController.navigateLosingBackStack(ChatDestinations.CHAT_LIST_SCREEN.name)
    }
}

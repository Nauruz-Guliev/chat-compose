package ru.kpfu.itis.authentication.presentation.screen.signup

import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.authentication.domain.usecase.SignUp
import ru.kpfu.itis.authentication_api.AuthenticationDestinations
import ru.kpfu.itis.chat_api.ChatDestinations
import ru.kpfu.itis.core_ui.base.BaseViewModel
import ru.kpfu.itis.core_ui.validation.EmailValidator
import ru.kpfu.itis.core_ui.validation.NameValidator
import ru.kpfu.itis.core_ui.validation.PasswordRepeatValidator
import ru.kpfu.itis.core_ui.validation.PasswordValidator
import ru.kpfu.itis.core_ui.validation.ValidationResult
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUp: SignUp,
    private val nameValidator: NameValidator,
    private val passwordValidator: PasswordValidator,
    private val passwordRepeatValidator: PasswordRepeatValidator,
    private val emailValidator: EmailValidator,
    private val navController: NavHostController
) : BaseViewModel<SignUpState, SignUpSideEffect>() {

    override val container: Container<SignUpState, SignUpSideEffect> =
        container(SignUpState())

    fun signUp(name: String, email: String, password: String, passwordRepeat: String) = intent {
        postSideEffect(SignUpSideEffect.ShowLoading)
        validate(name, email, password, passwordRepeat)
        if (state.emailValidationResult is ValidationResult.Success &&
            state.nameValidationResult is ValidationResult.Success &&
            state.passwordValidationResult is ValidationResult.Success &&
            state.passwordRepeatValidationResult is ValidationResult.Success
        ) {
            try {
                signUp.invoke(name, email, password)
                navigateMainScreen()
            } catch (ex: Exception) {
                postSideEffect(SignUpSideEffect.ExceptionHappened(ex))
            }
        } else {
            postSideEffect(SignUpSideEffect.ValidationFailure)
        }
    }

    fun resetState() = intent {
        reduce {
            state.copy(
                nameValidationResult = null,
                emailValidationResult = null,
                passwordValidationResult = null,
                passwordRepeatValidationResult = null
            )
        }
    }

    fun navigateSignIn() {
        navController.navigateSavingBackStack(AuthenticationDestinations.SIGNIN.name)
    }

    private fun validate(name: String, email: String, password: String, passwordRepeat: String) {
        container.stateFlow.value.apply {
            nameValidationResult = nameValidator(name)
            emailValidationResult = emailValidator(email)
            passwordValidationResult = passwordValidator(password)
            passwordRepeatValidationResult = passwordRepeatValidator(password, passwordRepeat)
        }
    }

    private fun navigateMainScreen() {
        navController.navigateLosingBackStack(ChatDestinations.AUTH_SUCCESS.name)
    }
}
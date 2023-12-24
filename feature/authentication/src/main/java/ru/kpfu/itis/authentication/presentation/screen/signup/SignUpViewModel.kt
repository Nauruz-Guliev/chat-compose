package ru.kpfu.itis.authentication.presentation.screen.signup

import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.authentication.domain.usecase.SignUp
import ru.kpfu.itis.authentication.presentation.validator.EmailValidator
import ru.kpfu.itis.authentication.presentation.validator.NameValidator
import ru.kpfu.itis.authentication.presentation.validator.PasswordValidator
import ru.kpfu.itis.authentication_api.AuthenticationDestinations
import ru.kpfu.itis.core.base.BaseViewModel
import ru.kpfu.itis.core.validation.ValidationResult
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUp: SignUp,
    private val nameValidator: NameValidator,
    private val passwordValidator: PasswordValidator,
    private val emailValidator: EmailValidator,
    private val navController: NavHostController
) : BaseViewModel<SignUpState, SignUpSideEffect>() {
    override val container: Container<SignUpState, SignUpSideEffect> =
        container(SignUpState())

    fun signUp(name: String, email: String, password: String) = intent {
        postSideEffect(SignUpSideEffect.ShowLoading)
        validate(name, email, password)
        if (state.emailValidationResult is ValidationResult.Success &&
            state.nameValidationResult is ValidationResult.Success &&
            state.passwordValidationResult is ValidationResult.Success
        ) {
            try {
                signUp.invoke(name, email, password)
                navController.navigate("main")
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
            )
        }
    }

    fun navigateSignIn() {
        navController.navigate(AuthenticationDestinations.SIGNIN.name)
    }

    private fun validate(name: String, email: String, password: String) = intent {
        reduce {
            state.copy(
                nameValidationResult = nameValidator(name),
                emailValidationResult = emailValidator(email),
                passwordValidationResult = passwordValidator(password)
            )
        }
    }
}
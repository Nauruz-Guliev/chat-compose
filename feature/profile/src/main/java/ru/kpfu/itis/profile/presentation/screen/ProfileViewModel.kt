package ru.kpfu.itis.profile.presentation.screen

import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.core_ui.base.BaseViewModel
import ru.kpfu.itis.core_ui.validation.EmailValidator
import ru.kpfu.itis.core_ui.validation.NameValidator
import ru.kpfu.itis.core_ui.validation.ValidationResult
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val nameValidator: NameValidator,
    private val emailValidator: EmailValidator,
    private val navController: NavHostController
) : BaseViewModel<ProfileState, ProfileSideEffect>() {

    override val container: Container<ProfileState, ProfileSideEffect> =
        container(ProfileState())

    fun signUp(name: String, email: String, password: String, passwordRepeat: String) = intent {
        postSideEffect(ProfileSideEffect.ShowLoading)
        validate(name, email, password, passwordRepeat)
        if (state.emailValidationResult is ValidationResult.Success &&
            state.nameValidationResult is ValidationResult.Success
        ) {

        } else {
            postSideEffect(ProfileSideEffect.ValidationFailure)
        }
    }

    fun resetState() = intent {
        reduce {
            state.copy(
                nameValidationResult = null,
                emailValidationResult = null
            )
        }
    }

    private fun validate(name: String, email: String, password: String, passwordRepeat: String) = intent {
        reduce {
            state.copy(
                nameValidationResult = nameValidator(name),
                emailValidationResult = emailValidator(email)
            )
        }
    }
}
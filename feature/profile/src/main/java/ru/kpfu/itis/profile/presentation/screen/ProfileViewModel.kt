package ru.kpfu.itis.profile.presentation.screen

import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.authentication_api.AuthenticationDestinations
import ru.kpfu.itis.core_ui.base.BaseViewModel
import ru.kpfu.itis.core_ui.validation.EmailValidator
import ru.kpfu.itis.core_ui.validation.NameValidator
import ru.kpfu.itis.core_ui.validation.ValidationResult
import ru.kpfu.itis.profile.domain.usecase.ClearUserId
import ru.kpfu.itis.profile.domain.usecase.GetChatUser
import ru.kpfu.itis.profile.domain.usecase.UpdateUser
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val nameValidator: NameValidator,
    private val emailValidator: EmailValidator,
    private val navController: NavHostController,
    private val getChatUser: GetChatUser,
    private val updateUser: UpdateUser,
    private val clearUserId: ClearUserId
) : BaseViewModel<ProfileState, ProfileSideEffect>() {

    override val container: Container<ProfileState, ProfileSideEffect> =
        container(ProfileState())

    init {
        loadUser()
    }

    fun updateProfile(name: String, email: String) = intent {
        validate(name, email)
        if (state.emailValidationResult is ValidationResult.Success &&
            state.nameValidationResult is ValidationResult.Success
        ) {
            updateUser(email, name)
        } else {
            postSideEffect(ProfileSideEffect.ValidationFailure)
        }
    }

    fun loadUser() = intent {
        try {
            val user = getChatUser()
            reduce { state.copy(user = user) }
            postSideEffect(ProfileSideEffect.UserLoaded(user))
        } catch (ex: Exception) {
            postSideEffect(ProfileSideEffect.ExceptionHappened(ex))
        }
    }

    fun exitProfile() = intent {
        resetState()
        clearUserId()
        navigateSignInScreen()
    }

    private fun resetState() = intent {
        reduce {
            state.copy(
                nameValidationResult = null,
                emailValidationResult = null,
                user = null
            )
        }
    }

    private fun navigateSignInScreen() {
        navController.navigateLosingBackStack(AuthenticationDestinations.SIGNIN.name)
    }

    private fun validate(name: String, email: String) {
        container.stateFlow.value.apply {
            nameValidationResult = nameValidator(name)
            emailValidationResult = emailValidator(email)
        }
    }
}
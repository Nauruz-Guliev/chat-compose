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
import ru.kpfu.itis.core_ui.validation.PasswordRepeatValidator
import ru.kpfu.itis.core_ui.validation.PasswordValidator
import ru.kpfu.itis.profile.domain.model.UpdateProfileModel
import ru.kpfu.itis.profile.domain.usecase.ClearUserId
import ru.kpfu.itis.profile.domain.usecase.GetChatUser
import ru.kpfu.itis.profile.domain.usecase.UpdateUser
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val nameValidator: NameValidator,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
    private val passwordRepeatValidator: PasswordRepeatValidator,
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

    fun loadUser() = intent {
        runCatching {
            getChatUser()
        }.onSuccess { user ->
            postSideEffect(ProfileSideEffect.UserLoaded(user))
            reduce { state.copy(user = user) }
        }.onFailure { exception ->
            postSideEffect(ProfileSideEffect.ExceptionHappened(exception))
        }
    }

    fun updateProfile(
        name: String,
        email: String,
        imageUrl: String?,
        password: String,
        passwordRepeat: String
    ) = intent {
        validate(name, email, password, passwordRepeat)
        if (isValidationSuccessful(state)) {
            runCatching {
                updateUser(
                    UpdateProfileModel(
                        name = name,
                        email = email,
                        password = password,
                        profileImage = imageUrl
                    )
                )
            }.onFailure { exception ->
                postSideEffect(ProfileSideEffect.ExceptionHappened(exception))
            }
        } else {
            reduce { state.copy(isValidationSuccessful = false) }
        }
    }

    private fun validate(name: String, email: String, password: String, passwordRepeat: String) {
        container.stateFlow.value.apply {
            nameValidationResult = nameValidator(name)
            emailValidationResult = emailValidator(email)
            passwordValidationResult = passwordValidator(password)
            passwordRepeatValidationResult = passwordRepeatValidator(passwordRepeat, password)
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
                user = null,
                isValidationSuccessful = true,
                passwordValidationResult = null,
                passwordRepeatValidationResult = null
            )
        }
    }

    private fun navigateSignInScreen() {
        navController.navigateLosingBackStack(AuthenticationDestinations.SIGNIN.name)
    }
}
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
import ru.kpfu.itis.profile.domain.model.UpdateProfileModel
import ru.kpfu.itis.profile.domain.usecase.ClearUserId
import ru.kpfu.itis.profile.domain.usecase.GetChatUser
import ru.kpfu.itis.profile.domain.usecase.SignOut
import ru.kpfu.itis.profile.domain.usecase.UpdateUser
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val nameValidator: NameValidator,
    private val emailValidator: EmailValidator,
    private val navController: NavHostController,
    private val getChatUser: GetChatUser,
    private val updateUser: UpdateUser,
    private val clearUserId: ClearUserId,
    private val signOut: SignOut
) : BaseViewModel<ProfileState, ProfileSideEffect>() {

    override val container: Container<ProfileState, ProfileSideEffect> = container(ProfileState())

    init {
        loadUser()
    }

    fun loadUser() = intent {
        runCatching {
            getChatUser()
        }.onSuccess { user ->
            postSideEffect(ProfileSideEffect.UserLoaded(user))
            reduce {
                state.copy(
                    user = user,
                    profileImage = user?.profileImage?.let {
                        ProfileImage.CURRENT
                    } ?: ProfileImage.DEFAULT
                )
            }
        }.onFailure { exception ->
            postSideEffect(ProfileSideEffect.ExceptionHappened(exception))
        }
    }

    fun updateProfile(
        name: String,
        email: String,
    ) = intent {
        validate(name, email)
        if (isValidationSuccessful(state)) {
            runCatching {
                updateUser(
                    UpdateProfileModel(
                        name = name,
                        email = email,
                        profileImage = state.pickedProfileImage ?: state.user?.profileImage
                    )
                )
            }.onFailure { exception ->
                postSideEffect(ProfileSideEffect.ExceptionHappened(exception))
            }.onSuccess {
                loadUser()
            }
        } else {
            reduce {
                state.copy(
                    isValidationSuccessful = false
                )
            }
        }
    }

    fun profileImagePicked(url: String?) = intent {
        reduce {
            state.copy(
                pickedProfileImage = url,
                profileImage = url?.let {
                    ProfileImage.PICKED
                } ?: state.user?.profileImage?.let {
                    ProfileImage.CURRENT
                } ?: ProfileImage.DEFAULT
            )
        }
    }

    private fun validate(name: String, email: String) {
        container.stateFlow.value.apply {
            nameValidationResult = nameValidator(name)
            emailValidationResult = emailValidator(email)
        }
    }

    fun exitProfile() = intent {
        signOut()
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
            )
        }
    }

    private fun navigateSignInScreen() {
        navController.navigateLosingBackStack(AuthenticationDestinations.SIGNIN.name)
    }
}
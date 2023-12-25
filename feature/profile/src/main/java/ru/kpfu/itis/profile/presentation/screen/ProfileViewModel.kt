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
import ru.kpfu.itis.profile.domain.usecase.GetChatUser
import ru.kpfu.itis.profile.domain.usecase.UpdateUser
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val nameValidator: NameValidator,
    private val emailValidator: EmailValidator,
    private val navController: NavHostController,
    private val getChatUser: GetChatUser,
    private val updateUser: UpdateUser
) : BaseViewModel<ProfileState, ProfileSideEffect>() {

    override val container: Container<ProfileState, ProfileSideEffect> =
        container(ProfileState())

    init {
        getUser()
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

    fun getUser() = intent {
        try {
            val user = getChatUser()
            reduce { state.copy(user = user) }
        } catch (ex: Exception) {
            postSideEffect(ProfileSideEffect.ExceptionHappened(ex))
        }
    }

    fun resetState() = intent {
        reduce {
            state.copy(
                nameValidationResult = null,
                emailValidationResult = null,
                user = null
            )
        }
    }

    private fun validate(name: String, email: String) = intent {
        reduce {
            state.copy(
                nameValidationResult = nameValidator(name),
                emailValidationResult = emailValidator(email)
            )
        }
    }
}
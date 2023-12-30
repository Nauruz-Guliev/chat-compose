package ru.kpfu.itis.profile.presentation.screen

import ru.kpfu.itis.core_data.ChatUser
import ru.kpfu.itis.core_ui.resource.Resource
import ru.kpfu.itis.core_ui.validation.ValidationResult

data class ProfileState(
    var nameValidationResult: ValidationResult<Resource.String>? = null,
    var emailValidationResult: ValidationResult<Resource.String>? = null,
    var passwordValidationResult: ValidationResult<Resource.String>? = null,
    var passwordRepeatValidationResult: ValidationResult<Resource.String>? = null,
    val user: ChatUser? = null,
    val isValidationSuccessful: Boolean = true
)

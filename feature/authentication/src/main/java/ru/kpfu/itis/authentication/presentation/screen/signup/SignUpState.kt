package ru.kpfu.itis.authentication.presentation.screen.signup

import ru.kpfu.itis.core_ui.resource.Resource
import ru.kpfu.itis.core_ui.validation.ValidationResult

data class SignUpState(
    val nameValidationResult: ValidationResult<Resource.String>? = null,
    val emailValidationResult: ValidationResult<Resource.String>? = null,
    val passwordValidationResult: ValidationResult<Resource.String>? = null,
    val passwordRepeatValidationResult: ValidationResult<Resource.String>? = null,
)
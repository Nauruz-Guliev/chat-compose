package ru.kpfu.itis.authentication.presentation.screen.signin

import ru.kpfu.itis.core_ui.resource.Resource
import ru.kpfu.itis.core_ui.validation.ValidationResult

data class SignInState(
    val emailValidationResult: ValidationResult<Resource.String>? = null,
    val passwordValidationResult: ValidationResult<Resource.String>? = null,
)
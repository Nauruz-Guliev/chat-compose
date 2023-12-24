package ru.kpfu.itis.authentication.presentation.screen.signin

import ru.kpfu.itis.core.resource.Resource
import ru.kpfu.itis.core.validation.ValidationResult

data class SignInState(
    val emailValidationResult: ValidationResult<Resource.String>? = null,
    val passwordValidationResult: ValidationResult<Resource.String>? = null,
)
package ru.kpfu.itis.authentication.presentation.screen.signin

import ru.kpfu.itis.coreui.resource.Resource
import ru.kpfu.itis.coreui.validation.ValidationResult

data class SignInState(
    val emailValidationResult: ValidationResult<Resource.String>? = null,
    val passwordValidationResult: ValidationResult<Resource.String>? = null,
    val isLoading: Boolean = false
)

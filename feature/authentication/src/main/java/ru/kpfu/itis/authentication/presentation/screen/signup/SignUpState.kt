package ru.kpfu.itis.authentication.presentation.screen.signup

import ru.kpfu.itis.coreui.resource.Resource
import ru.kpfu.itis.coreui.validation.ValidationResult

data class SignUpState(
    var nameValidationResult: ValidationResult<Resource.String>? = null,
    var emailValidationResult: ValidationResult<Resource.String>? = null,
    var passwordValidationResult: ValidationResult<Resource.String>? = null,
    var passwordRepeatValidationResult: ValidationResult<Resource.String>? = null,
    val isLoading: Boolean = false
)

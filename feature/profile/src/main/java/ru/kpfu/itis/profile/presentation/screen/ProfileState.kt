package ru.kpfu.itis.profile.presentation.screen

import ru.kpfu.itis.core_ui.resource.Resource
import ru.kpfu.itis.core_ui.validation.ValidationResult

data class ProfileState(
    val nameValidationResult: ValidationResult<Resource.String>? = null,
    val emailValidationResult: ValidationResult<Resource.String>? = null,
)

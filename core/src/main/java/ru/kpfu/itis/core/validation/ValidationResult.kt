package ru.kpfu.itis.core.validation

import ru.kpfu.itis.core.resource.Resource

sealed interface ValidationResult<out T> {

    data object Success : ValidationResult<Nothing>
    data class Failure(val stringResource: Resource.String) : ValidationResult<Nothing>
}
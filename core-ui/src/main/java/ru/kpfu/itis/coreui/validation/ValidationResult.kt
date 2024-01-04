package ru.kpfu.itis.coreui.validation

import ru.kpfu.itis.coreui.resource.Resource

sealed interface ValidationResult<out T> {

   data object Success : ValidationResult<Nothing>
    data class Failure(val stringResource: Resource.String) : ValidationResult<Nothing>
}

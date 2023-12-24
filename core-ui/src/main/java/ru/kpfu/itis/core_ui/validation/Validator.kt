package ru.kpfu.itis.core_ui.validation

import ru.kpfu.itis.core_ui.resource.Resource

interface Validator<T> {
    operator fun invoke(field: T, secondField: T? = null): ValidationResult<Resource.String>
}
package ru.kpfu.itis.core.validation

import ru.kpfu.itis.core.resource.Resource

interface Validator<T> {
    operator fun invoke(field: T, secondField: T? = null): ValidationResult<Resource.String>
}
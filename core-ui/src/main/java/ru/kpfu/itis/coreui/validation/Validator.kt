package ru.kpfu.itis.coreui.validation

import ru.kpfu.itis.coreui.resource.Resource

interface Validator<T> {
    operator fun invoke(field: T, secondField: T? = null): ValidationResult<Resource.String>
}

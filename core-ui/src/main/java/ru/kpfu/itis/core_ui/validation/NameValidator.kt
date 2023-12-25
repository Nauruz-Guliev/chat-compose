package ru.kpfu.itis.core_ui.validation

import ru.kpfu.itis.core.R
import ru.kpfu.itis.core_ui.resource.Resource
import javax.inject.Inject

class NameValidator @Inject constructor() : Validator<String> {
    override fun invoke(field: String, secondField: String?): ValidationResult<Resource.String> {
        return if (field.isBlank()) {
            ValidationResult.Failure(
                Resource.String(
                    R.string.error_validation_empty,
                    R.string.name
                )
            )
        } else if (field.length < 4) {
            ValidationResult.Failure(
                Resource.String(
                    R.string.error_short_field_length,
                    R.string.name,
                )
            )
        } else {
            ValidationResult.Success
        }
    }
}
package ru.kpfu.itis.authentication.presentation.validator

import ru.kpfu.itis.core.resource.Resource
import ru.kpfu.itis.core.validation.ValidationResult
import ru.kpfu.itis.core.validation.Validator
import javax.inject.Inject
import ru.kpfu.itis.core.R as CoreResources

private const val MIN_LENGTH = 4

class NameValidator @Inject constructor() : Validator<String> {
    override fun invoke(field: String): ValidationResult<Resource.String> {
        return if (field.isBlank()) {
            ValidationResult.Failure(
                Resource.String(
                    CoreResources.string.error_validation_empty,
                    CoreResources.string.name
                )
            )
        } else if (field.length < MIN_LENGTH) {
            ValidationResult.Failure(
                Resource.String(
                    CoreResources.string.error_short_field_length,
                    CoreResources.string.name,
                )
            )
        } else {
            ValidationResult.Success
        }
    }
}
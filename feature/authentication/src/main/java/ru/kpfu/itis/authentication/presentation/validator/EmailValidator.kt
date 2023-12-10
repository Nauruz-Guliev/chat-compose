package ru.kpfu.itis.authentication.presentation.validator

import android.util.Patterns
import ru.kpfu.itis.core.resource.Resource
import ru.kpfu.itis.core.validation.ValidationResult
import ru.kpfu.itis.core.validation.Validator
import javax.inject.Inject
import ru.kpfu.itis.core.R as CoreResources

class EmailValidator @Inject constructor() : Validator<String> {
    override fun invoke(field: String): ValidationResult<Resource.String> {
        return if (field.isBlank()) {
            ValidationResult.Failure(
                Resource.String(
                    CoreResources.string.error_validation_empty,
                    CoreResources.string.email
                )
            )
        } else if (!Patterns.EMAIL_ADDRESS.matcher(field).matches()) {
            ValidationResult.Failure(
                Resource.String(CoreResources.string.error_validation_not_email)
            )
        } else {
            ValidationResult.Success
        }
    }
}
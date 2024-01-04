package ru.kpfu.itis.coreui.validation

import ru.kpfu.itis.core.R
import ru.kpfu.itis.coreui.resource.Resource
import javax.inject.Inject

private const val MIN_PASSWORD_LENGTH = 8

class PasswordValidator @Inject constructor() : Validator<String> {
    override fun invoke(field: String, secondField: String?): ValidationResult<Resource.String> {
        return if (field.isBlank()) {
            ValidationResult.Failure(
                Resource.String(
                    R.string.error_validation_empty,
                    R.string.password
                )
            )
        } else if (field.length < MIN_PASSWORD_LENGTH) {
            ValidationResult.Failure(
                Resource.String(
                    R.string.error_short_field_length,
                    R.string.password,
                )
            )
        } else {
            ValidationResult.Success
        }
    }
}

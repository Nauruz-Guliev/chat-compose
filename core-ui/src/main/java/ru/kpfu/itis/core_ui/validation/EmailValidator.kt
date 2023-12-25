package ru.kpfu.itis.core_ui.validation

import android.util.Patterns
import ru.kpfu.itis.core.R
import ru.kpfu.itis.core_ui.resource.Resource
import javax.inject.Inject

class EmailValidator @Inject constructor() : Validator<String> {
    override fun invoke(field: String, secondField: String?): ValidationResult<Resource.String> {
        return if (field.isBlank()) {
            ValidationResult.Failure(
                Resource.String(
                    R.string.error_validation_empty,
                    R.string.email
                )
            )
        } else if (!Patterns.EMAIL_ADDRESS.matcher(field).matches()) {
            ValidationResult.Failure(
                Resource.String(R.string.error_validation_not_email)
            )
        } else {
            ValidationResult.Success
        }
    }
}
package ru.kpfu.itis.authentication.presentation.validator

import ru.kpfu.itis.core.resource.Resource
import ru.kpfu.itis.core.validation.ValidationResult
import ru.kpfu.itis.core.validation.Validator
import javax.inject.Inject
import ru.kpfu.itis.core.R as CoreR

class PasswordRepeatValidator @Inject constructor() : Validator<String> {
    override fun invoke(field: String, secondField: String?): ValidationResult<Resource.String> {
        return if(field == secondField) {
            ValidationResult.Success
        } else {
            ValidationResult.Failure(
                Resource.String(CoreR.string.error_different_passwords)
            )
        }
    }
}
package ru.kpfu.itis.coreui.validation

import ru.kpfu.itis.core.R
import ru.kpfu.itis.coreui.resource.Resource
import javax.inject.Inject

class PasswordRepeatValidator @Inject constructor() : Validator<String> {
    override fun invoke(field: String, secondField: String?): ValidationResult<Resource.String> {
        return if(field == secondField) {
            ValidationResult.Success
        } else {
            ValidationResult.Failure(
                Resource.String(R.string.error_different_passwords)
            )
        }
    }
}

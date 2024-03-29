package ru.startandroid.testauthproject.utils.validation

import ru.startandroid.testauthproject.presentation.activities.auth.flow.AuthFlowErrorModel

/**
 * source for handling validation errors
 * with ability return errors to view between callback
 */
interface IValidationHandler {

    /**
     * @param email
     * @param password
     * @param confirmPassword
     * @param agreeTerms
     */
    fun validationAuthCases(
        email: String,
        password: String?,
        confirmPassword: String?,
        agreeTerms: Boolean?
    ): AuthFlowErrorModel
}

internal class ValidationHandlerImpl : IValidationHandler {

    private val validator: IValidator=ValidatorImpl()

    override fun validationAuthCases(
        email: String,
        password: String?,
        confirmPassword: String?,
        agreeTerms: Boolean?
    ): AuthFlowErrorModel {

        val errors=HashMap<AuthFlowErrorModel.ErrorType, ValidationErrorMessage>()

        email.apply {
            validator.isValidEmail(email)?.let {
                errors.put(AuthFlowErrorModel.ErrorType.EMAIL_ERROR, it)
            }
        }

        password?.apply {
            validator.isValidPassword(password)?.let {
                errors[AuthFlowErrorModel.ErrorType.PASSWORD_ERROR]=it
            }

            confirmPassword?.apply {
                validator.isValidConfirmPassword(password, confirmPassword)?.let {
                    errors[AuthFlowErrorModel.ErrorType.PASSWORD_CONFIRM_ERROR]=it
                }

            }
        }

        agreeTerms?.apply {
            if(!this)
                errors[AuthFlowErrorModel.ErrorType.TERMS_CONDITION_ERROR]=
                    ValidationErrorMessage.TERMS_CONDITION_VALIDATION_ERROR
        }

        return AuthFlowErrorModel(errors)
    }
}
package ru.startandroid.testauthproject.domain

import androidx.lifecycle.ViewModel
import ru.startandroid.testauthproject.presentation.activities.auth.flow.AuthFlowErrorModel
import ru.startandroid.testauthproject.presentation.activities.auth.flow.AuthFlowModel
import ru.startandroid.testauthproject.presentation.activities.auth.flow.IAuthFlow
import ru.startandroid.testauthproject.utils.validation.IValidationHandler
import ru.startandroid.testauthproject.utils.validation.ValidationHandlerImpl

/**
 *
 */
class AuthViewModel : ViewModel() {

    private val validationHandler: IValidationHandler = ValidationHandlerImpl()

    /**
     *
     */
    fun authRequest(flowModel: AuthFlowModel, callback: IAuthFlow.IAuthCallback) {
        val validation: AuthFlowErrorModel =doValidation(flowModel)
        if (validation.emptyErrors()) {

        } else {
            callback.showError(validation)
        }
    }

    /**
     *
     */
    override fun onCleared() {
        super.onCleared()
    }

    private fun doValidation(flowModel: AuthFlowModel): AuthFlowErrorModel=
        validationHandler.validationAuthCases(
            flowModel.email,
            flowModel.password,
            flowModel.passwordConfirm,
            flowModel.agreeTerms
        )
}
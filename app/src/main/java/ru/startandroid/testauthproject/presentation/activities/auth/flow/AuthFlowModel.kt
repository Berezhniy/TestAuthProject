package ru.startandroid.testauthproject.presentation.activities.auth.flow

class AuthFlowModel(var type: IAuthFlow.AuthType? = null,
                    var email : String? = null,
                    var password : String? = null,
                    var passwordConfirm : String? = null,
                    var isAcceptTerms : Boolean? = false) {

}
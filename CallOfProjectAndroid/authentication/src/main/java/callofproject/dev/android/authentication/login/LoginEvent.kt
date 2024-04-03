package callofproject.dev.adroid.app.authentication.login.presentation

import callofproject.dev.adroid.servicelib.dto.UserLoginDTO

sealed class LoginEvent {
    data object OnRegisterButtonClick : LoginEvent()
    data class OnLoginButtonClick(val username: String, val password: String) : LoginEvent()
}
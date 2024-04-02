package callofproject.dev.adroid.app.login.presentation

import callofproject.dev.adroid.servicelib.dto.UserLoginDTO
import callofproject.dev.adroid.servicelib.dto.UserRegisterDTO

sealed class LoginEvent {
    data class OnRegisterButtonClick(val registerDTO: UserRegisterDTO) : LoginEvent()
    data class OnLoginButtonClick(val loginDTO: UserLoginDTO) : LoginEvent()
}
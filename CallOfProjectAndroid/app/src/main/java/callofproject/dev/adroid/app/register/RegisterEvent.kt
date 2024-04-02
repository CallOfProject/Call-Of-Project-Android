package callofproject.dev.adroid.app.register

import callofproject.dev.adroid.servicelib.dto.UserRegisterDTO

sealed class RegisterEvent {
    data class OnClickRegisterBtn(val userRegisterDTO: UserRegisterDTO) : RegisterEvent()
}
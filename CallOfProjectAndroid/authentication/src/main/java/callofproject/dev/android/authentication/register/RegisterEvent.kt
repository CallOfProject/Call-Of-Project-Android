package callofproject.dev.android.authentication.register

import callofproject.dev.adroid.servicelib.dto.UserRegisterDTO

sealed class RegisterEvent {
    data class OnClickRegisterBtn(val userRegisterDTO: UserRegisterDTO) : RegisterEvent()
    data class OnRegisterDtoChange(var userRegisterDTO: UserRegisterDTO) : RegisterEvent()
}
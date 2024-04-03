package callofproject.dev.adroid.app.authentication.register

import callofproject.dev.adroid.servicelib.dto.UserRegisterDTO

data class RegisterState(
    val isSuccess: Boolean = false,
    val isClickedBtn: Boolean = false,
    var userRegisterDto: UserRegisterDTO = UserRegisterDTO()
)

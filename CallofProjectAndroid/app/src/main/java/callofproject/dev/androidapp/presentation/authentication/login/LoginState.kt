package callofproject.dev.androidapp.presentation.authentication.login

import callofproject.dev.androidapp.domain.dto.UserLoginDTO

data class LoginState(
    val userLoginDTO: UserLoginDTO = UserLoginDTO(),
    val isLoading: Boolean = false,
    val error: String = ""
)
package callofproject.dev.adroid.app.authentication.login.presentation

import callofproject.dev.adroid.servicelib.dto.AuthenticationResponse

data class LoginState(
    var isClickedLogin: Boolean = false,
    var isSuccess: Boolean = false,
    var authResponse: AuthenticationResponse? = null
)

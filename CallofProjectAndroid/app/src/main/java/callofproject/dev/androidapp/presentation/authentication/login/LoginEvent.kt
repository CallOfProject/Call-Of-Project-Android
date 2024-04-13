package callofproject.dev.androidapp.presentation.authentication.login

sealed class LoginEvent {
    data object OnRegisterButtonClick : LoginEvent()
    data object OnLoginButtonClick : LoginEvent()

    data class OnUsernameChange(val username: String) : LoginEvent()
    data class OnPasswordChange(val password: String) : LoginEvent()
}
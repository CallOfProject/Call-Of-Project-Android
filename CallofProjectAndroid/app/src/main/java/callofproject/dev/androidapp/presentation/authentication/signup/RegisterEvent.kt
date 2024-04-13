package callofproject.dev.androidapp.presentation.authentication.signup

sealed class RegisterEvent {
    data object OnClickRegisterBtn : RegisterEvent()
    data class OnFirstNameChange(val firstName: String) : RegisterEvent()
    data class OnMiddleNameChange(val middleName: String) : RegisterEvent()
    data class OnLastNameChange(val lastName: String) : RegisterEvent()
    data class OnUsernameChange(val username: String) : RegisterEvent()
    data class OnPasswordChange(val password: String) : RegisterEvent()
    data class OnBirthDateChange(val birthDate: String) : RegisterEvent()
    data class OnEmailChange(val email: String) : RegisterEvent()
}
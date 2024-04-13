package callofproject.dev.androidapp.presentation.authentication.signup

import callofproject.dev.androidapp.domain.dto.UserRegisterDTO


data class RegisterState(
    val userRegisterDTO: UserRegisterDTO = UserRegisterDTO()
)

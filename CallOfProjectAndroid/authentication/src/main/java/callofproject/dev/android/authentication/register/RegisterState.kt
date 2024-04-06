package callofproject.dev.adroid.app.authentication.register

import callofproject.dev.adroid.servicelib.dto.UserRegisterDTO

data class RegisterState(
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val username: String = "",
    val password: String = "",
    val birthDate: String = ""

)

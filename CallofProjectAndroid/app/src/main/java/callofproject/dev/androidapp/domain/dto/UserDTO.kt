package callofproject.dev.androidapp.domain.dto

import com.google.gson.annotations.SerializedName

data class UserDTO(
    val username: String = "",
    val email: String = "",
    @SerializedName("first_name")
    val firstName: String = "",
    @SerializedName("middle_name")
    val middleName: String = "",
    @SerializedName("last_name")
    val lastName: String = ""
)
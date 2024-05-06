package callofproject.dev.androidapp.domain.dto.user_profile

import callofproject.dev.androidapp.domain.dto.UserDTO
import com.google.gson.annotations.SerializedName

data class UserWithProfileDTO(
    @SerializedName("user_id")
    val id: String = "",

    val user: UserDTO = UserDTO(),

    val profile: UserProfileDTO = UserProfileDTO()
)
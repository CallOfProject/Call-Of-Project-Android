package callofproject.dev.androidapp.domain.dto

import callofproject.dev.androidapp.domain.dto.user_profile.UserProfileDTO
import com.google.gson.annotations.SerializedName

data class UserWithProfileDTO(
    @SerializedName("user_id")
    val id: String = "",
    val user: UserDTO = UserDTO(),
    val profile: UserProfileDTO = UserProfileDTO()
)
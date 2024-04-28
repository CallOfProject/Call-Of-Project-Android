package callofproject.dev.androidapp.domain.dto.connection

import com.google.gson.annotations.SerializedName

data class UserConnectionDTO(
    @SerializedName("user_id")
    val userId: String = "",
    @SerializedName("username")
    val username: String = "",
    @SerializedName("profile_photo")
    val profilePhoto: String = ""
)

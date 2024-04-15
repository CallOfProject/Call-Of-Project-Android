package callofproject.dev.androidapp.domain.dto.user_profile

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileUpdateDTO(
    @SerializedName("user_rate")
    val userRate: Double = 0.0,

    @SerializedName("user_feedback_rate")
    val userFeedbackRate: Double = 0.0,

    @SerializedName("user_id")
    val userId: String = "",

    @SerializedName("about_me")
    val aboutMe: String = ""
)

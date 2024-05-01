package callofproject.dev.androidapp.domain.dto.user_profile

import com.google.gson.annotations.SerializedName

data class UserTagDTO(
    @SerializedName("tag_name")
    val tagName: String = "",
    @SerializedName("tag_id")
    val tagId: String = ""
)

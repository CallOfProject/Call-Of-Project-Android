package callofproject.dev.androidapp.domain.dto.user_profile.link

import com.google.gson.annotations.SerializedName

data class LinkCreateDTO(
    @SerializedName("user_id")
    val userId: String,

    @SerializedName("link_title")
    val linkTitle: String,

    val link: String
)

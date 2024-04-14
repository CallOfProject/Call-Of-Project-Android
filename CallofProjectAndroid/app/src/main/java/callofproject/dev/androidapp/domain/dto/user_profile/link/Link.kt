package callofproject.dev.androidapp.domain.dto.user_profile.link

import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("link_id")
    val linkId: Long,

    val linkTitle: String,

    val link: String
)

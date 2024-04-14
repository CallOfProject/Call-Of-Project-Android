package callofproject.dev.androidapp.domain.dto.user_profile.link

import com.google.gson.annotations.SerializedName

data class LinkDTO(
    val linkId: Long = -1L,

    @SerializedName("link_title")
    val linkTitle: String = "",

    val link: String = ""
)
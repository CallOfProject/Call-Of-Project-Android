package callofproject.dev.androidapp.domain.dto

import com.google.gson.annotations.SerializedName

data class LinkDTO(
    val linkId: Long,
    @SerializedName("link_title")
    val linkTitle: String,
    val link: String
)
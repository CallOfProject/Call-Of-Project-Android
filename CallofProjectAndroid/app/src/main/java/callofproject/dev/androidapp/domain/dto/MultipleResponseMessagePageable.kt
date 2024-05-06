package callofproject.dev.androidapp.domain.dto

import com.google.gson.annotations.SerializedName

data class MultipleResponseMessagePageable<T>(
    @SerializedName("total_page")
    val totalPage: Long,

    @SerializedName("page")
    val page: Int,

    @SerializedName("item_count")
    val itemCount: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("object")
    val `object`: T
)
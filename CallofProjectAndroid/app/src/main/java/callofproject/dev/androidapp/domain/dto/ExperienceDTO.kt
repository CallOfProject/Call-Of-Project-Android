package callofproject.dev.androidapp.domain.dto

import com.google.gson.annotations.SerializedName

data class ExperienceDTO(
    @SerializedName("experience_id")
    val experienceId: String = "",
    @SerializedName("company_name")
    val companyName: String = "",
    val description: String = "",
    @SerializedName("company_website_link")
    val companyWebsiteLink: String = "",
    @SerializedName("start_date")
    val startDate: String = "",
    @SerializedName("finish_date")
    val finishDate: String = "",
    @SerializedName("is_continue")
    val isContinue: Boolean = false,
    val position: String = ""
) 
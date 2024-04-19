package callofproject.dev.androidapp.domain.dto.user_profile.experience

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
    val startDate: String = "Start Date",

    @SerializedName("finish_date")
    val finishDate: String = "Finish Date",

    @SerializedName("is_continue")
    val isContinue: Boolean = false,

    val position: String = ""
) 
package callofproject.dev.androidapp.domain.dto.user_profile.experience

import com.google.gson.annotations.SerializedName

data class ExperienceCreateDTO(
    @SerializedName("user_id")
    val userId: String,

    @SerializedName("company_name")
    val companyName: String,

    val description: String,

    @SerializedName("company_website")
    val companyWebSite: String,

    @SerializedName("job_definition")
    val jobDefinition: String,

    @SerializedName("start_date")
    val startDate: String,

    @SerializedName("finish_date")
    val finishDate: String,

    @SerializedName("is_continue")
    val isContinue: Boolean
)

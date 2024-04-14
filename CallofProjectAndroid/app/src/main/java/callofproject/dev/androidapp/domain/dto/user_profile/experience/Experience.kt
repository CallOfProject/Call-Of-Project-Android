package callofproject.dev.androidapp.domain.dto.user_profile.experience

import com.google.gson.annotations.SerializedName

data class Experience(
    @SerializedName("experience_id")
    val experienceId: String,

    val companyUniqueId: String,

    val companyName: String,

    val description: String,

    val companyWebsiteLink: String,

    val startDate: String,

    val finishDate: String,

    val jobDefinition: String,

    val `continue`: Boolean
)
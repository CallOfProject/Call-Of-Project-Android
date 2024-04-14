package callofproject.dev.androidapp.domain.dto.user_profile.education

import com.google.gson.annotations.SerializedName

data class EducationCreateDTO(
    @SerializedName("user_id")
    val userId: String,

    @SerializedName("school_name")
    val schoolName: String,

    val department: String,

    val description: String,

    @SerializedName("start_date")
    val startDate: String,

    @SerializedName("finish_date")
    val finishDate: String,

    @SerializedName("is_continue")
    val isContinue: Boolean,

    val gpa: Double
)

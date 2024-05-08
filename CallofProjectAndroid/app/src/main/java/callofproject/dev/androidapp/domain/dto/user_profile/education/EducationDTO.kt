package callofproject.dev.androidapp.domain.dto.user_profile.education

import com.google.gson.annotations.SerializedName

data class EducationDTO(
    @SerializedName("education_id")
    val educationId: String = "",
    @SerializedName("school_name")
    val schoolName: String = "",
    @SerializedName("department")
    val department: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("start_date")
    val startDate: String = "Start Date",
    @SerializedName("finish_date")
    val finishDate: String = "Finish Date",
    @SerializedName("is_continue")
    var isContinue: Boolean = false,
    val gpa: Double = 0.0
)
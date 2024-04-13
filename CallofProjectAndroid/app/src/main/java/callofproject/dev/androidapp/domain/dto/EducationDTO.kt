package callofproject.dev.androidapp.domain.dto

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
    val startDate: String = "",
    @SerializedName("finish_date")
    val finishDate: String = "",
    @SerializedName("is_continue")
    val isContinue: Boolean = false,
    val gpa: Double = 0.0
)
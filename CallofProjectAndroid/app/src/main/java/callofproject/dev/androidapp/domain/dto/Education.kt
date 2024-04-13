package callofproject.dev.androidapp.domain.dto

import com.google.gson.annotations.SerializedName

data class Education(
    @SerializedName("education_id")
    val educationId: String = "",
    val universityId: String = "",
    val schoolName: String = "",
    val department: String = "",
    val description: String = "",
    val startDate: String = "",
    val finishDate: String = "",
    val `continue`: Boolean = false,
    val gpa: Double = 0.0
)

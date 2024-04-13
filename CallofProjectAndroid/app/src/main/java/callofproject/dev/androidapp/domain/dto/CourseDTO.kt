package callofproject.dev.androidapp.domain.dto

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class CourseDTO(
    @SerializedName("course_id")
    val courseId: UUID,
    @SerializedName("organization")
    val organization: String,
    @SerializedName("course_name")
    val courseName: String,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("finish_date")
    val finishDate: String,
    @SerializedName("is_continue")
    val isContinue: Boolean,
    val description: String
)
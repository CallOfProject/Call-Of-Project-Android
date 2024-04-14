package callofproject.dev.androidapp.domain.dto.user_profile.course

import com.google.gson.annotations.SerializedName

data class CourseDTO(
    @SerializedName("course_id")
    val courseId: String = "",
    @SerializedName("organization")
    val organization: String = "",
    @SerializedName("course_name")
    val courseName: String = "",
    @SerializedName("start_date")
    val startDate: String = "",
    @SerializedName("finish_date")
    val finishDate: String = "",
    @SerializedName("is_continue")
    val isContinue: Boolean = false,
    val description: String = ""
)
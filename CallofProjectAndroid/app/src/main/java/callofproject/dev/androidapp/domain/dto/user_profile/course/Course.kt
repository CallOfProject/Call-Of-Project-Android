package callofproject.dev.androidapp.domain.dto.user_profile.course

import com.google.gson.annotations.SerializedName

data class Course(
    @SerializedName("course_id")
    val courseId: String,

    val uniqueCourseId: String,

    val courseName: String,

    val startDate: String,

    val finishDate: String,

    val description: String,

    val courseOrganization: CourseOrganization,

    val `continue`: Boolean
)

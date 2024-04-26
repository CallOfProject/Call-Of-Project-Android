package callofproject.dev.androidapp.domain.dto.filter

import com.google.gson.annotations.SerializedName

data class ProjectFilterDTO(
    @SerializedName("profession_level")
    val professionLevel: String? = null,

    @SerializedName("project_level")
    val projectLevel: String? = null,

    val degree: String? = null,

    @SerializedName("feedback_time_range")
    val feedbackTimeRange: String? = null,

    @SerializedName("interview_type")
    val interviewType: String? = null,

    @SerializedName("project_status")
    val projectStatus: String? = null,

    @SerializedName("start_date")
    var startDate: String? = null,

    @SerializedName("expected_completion_date")
    var expectedCompletionDate: String? = null,

    @SerializedName("application_deadline")
    var applicationDeadline: String? = null,

    val keyword: String? = ""
)

package callofproject.dev.androidapp.domain.dto.project

import com.google.gson.annotations.SerializedName

data class ProjectOverviewDTO(
    @SerializedName("project_id") val projectId: String = "",
    @SerializedName("project_image_path") val projectImagePath: String = "",
    @SerializedName("project_title") val projectTitle: String = "",
    @SerializedName("project_summary") val projectSummary: String = "",
    @SerializedName("project_aim") val projectAim: String = "",
    @SerializedName("application_deadline") val applicationDeadline: String = "",
    @SerializedName("expected_completion_date") val expectedCompletionDate: String = "",
    @SerializedName("start_date") val startDate: String = "",
    @SerializedName("max_participant") val maxParticipant: Int = 0,
    @SerializedName("techinical_requirements") val technicalRequirements: String = "",
    @SerializedName("special_requirements") val specialRequirements: String = "",
    @SerializedName("project_profession_level") val professionLevel: String = "",
    @SerializedName("project_degree") val degree: String = "",
    @SerializedName("project_level") val projectLevel: String = "",
    @SerializedName("interview_type") val interviewType: String = "",
    @SerializedName("project_owner_name") val projectOwnerName: String = "",
    @SerializedName("feedback_time_range") val feedbackTimeRange: String = "",
    @SerializedName("project_status") val projectStatus: String = "",
    @SerializedName("project_tags") val projectTags: List<ProjectTag> = emptyList()
)

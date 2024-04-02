package callofproject.dev.adroid.servicelib.dto

import com.google.gson.annotations.SerializedName

data class ProjectDetailDTO(

    @SerializedName("project_id")
    val projectId: String,
    @SerializedName("project_image_path")
    val projectImagePath: String,
    @SerializedName("project_title")
    val projectTitle: String,
    @SerializedName("project_summary")
    val projectSummary: String,
    @SerializedName("project_aim")
    val projectAim: String,
    @SerializedName("project_description")
    val projectDescription: String,
    @SerializedName("application_deadline")
    val applicationDeadline: String,
    @SerializedName("expected_completion_date")
    val expectedCompletionDate: String,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("max_participant")
    val maxParticipant: Int,
    @SerializedName("technical_requirements")
    val technicalRequirements: List<String>,
    @SerializedName("special_requirements")
    val specialRequirements: List<String>,
    @SerializedName("project_profession_level")
    val projectProfessionLevel: String,
    @SerializedName("project_sector")
    val projectSector: String,
    @SerializedName("project_degree")
    val projectDegree: String,
    @SerializedName("project_level")
    val projectLevel: String,
    @SerializedName("interview_type")
    val interviewType: String,
    @SerializedName("project_owner_name")
    val projectOwnerName: String,
    @SerializedName("feedback_time_range")
    val feedbackTimeRange: String,
    @SerializedName("project_status")
    val projectStatus: String,
    @SerializedName("project_tags")
    val projectTags: List<ProjectTag>,
    @SerializedName("admin_note")
    val adminNote: String,
    @SerializedName("project_participants")
    val projectParticipants: List<ProjectParticipantDTO>
)

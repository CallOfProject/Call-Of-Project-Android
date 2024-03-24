package callofproject.dev.adroid.servicelib.dto

data class ProjectDetailDTO(
    val project_id: String,
    val project_image_path: String,
    val project_title: String,
    val project_summary: String,
    val project_aim: String,
    val project_description: String,
    val application_deadline: String,
    val expected_completion_date: String,
    val start_date: String,
    val max_participant: Int,
    val technical_requirements: List<String>,
    val special_requirements: List<String>,
    val project_profession_level: String,
    val project_sector: String,
    val project_degree: String,
    val project_level: String,
    val interview_type: String,
    val project_owner_name: String,
    val feedback_time_range: String,
    val project_status: String,
    val project_tags: List<ProjectTag>,
    val admin_note: String,
    val project_participants: List<ProjectParticipantDTO>
)

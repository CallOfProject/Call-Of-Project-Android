package callofproject.dev.adroid.servicelib.dto

data class ProjectParticipantDTO(
    val project_id: String,
    val user_id: String,
    val username: String,
    val full_name: String,
    val join_date: String
)

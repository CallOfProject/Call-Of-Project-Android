package callofproject.dev.androidapp.domain.dto.project

import com.google.gson.annotations.SerializedName

data class ProjectParticipantRequestDTO(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("project_id")
    val projectId: String,
    @SerializedName("request_id")
    val requestId: String
)

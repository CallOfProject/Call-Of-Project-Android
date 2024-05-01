package callofproject.dev.androidapp.domain.dto.search

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class ProjectDTO(
    @SerializedName("project_id")
    val projectId: UUID,

    @SerializedName("project_name")
    val projectName: String,

    @SerializedName("project_image")
    val projectImage: String,

    @SerializedName("project_summary")
    val projectSummary: String,

    @SerializedName("project_owner")
    val projectOwner: String,

    @SerializedName("project_status")
    val projectStatus: EProjectStatus,

    @SerializedName("application_deadline")
    val applicationDeadline: String,

    @SerializedName("creation_date")
    val creationDate: String,

    @SerializedName("start_date")
    val startDate: String,

    @SerializedName("expected_completion_date")
    val expectedCompletionDate: String,
)

package callofproject.dev.androidapp.domain.dto.project

import com.google.gson.annotations.SerializedName
import java.util.Date
import java.util.UUID

data class ProjectDiscoveryDTO(
    @SerializedName("project_id")
    val projectId: UUID,
    @SerializedName("project_image_path")
    val projectImagePath: String,
    @SerializedName("project_title")
    val projectTitle: String,
    @SerializedName("project_summary")
    val projectSummary: String,
    @SerializedName("project_owner")
    val projectOwnerName: String,
    @SerializedName("application_deadline")
    val applicationDeadline: String,
    @SerializedName("creation_date")
    val creationDate: String,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("expected_completion_date")
    val expectedCompletionDate: String
) {
    override fun toString(): String {
        return "ProjectDiscoveryDTO(projectId=$projectId, projectImagePath='$projectImagePath', projectTitle='$projectTitle', projectSummary='$projectSummary', projectOwnerName='$projectOwnerName', applicationDeadline='$applicationDeadline')"
    }
}
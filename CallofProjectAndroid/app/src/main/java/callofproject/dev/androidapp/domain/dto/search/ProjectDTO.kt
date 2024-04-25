package callofproject.dev.androidapp.domain.dto.search

import java.util.UUID

data class ProjectDTO(
    val projectId: UUID,
    val projectName: String,
    val projectImage: String,
    val projectSummary: String,
    val projectOwner: String,
    val projectStatus: EProjectStatus
)

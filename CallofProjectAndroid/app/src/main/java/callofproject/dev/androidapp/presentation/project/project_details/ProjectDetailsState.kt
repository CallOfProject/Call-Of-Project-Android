package callofproject.dev.androidapp.presentation.project.project_details

import callofproject.dev.androidapp.domain.dto.ProjectDetailDTO

data class ProjectDetailsState(
    val projectDetailsDTO: ProjectDetailDTO = ProjectDetailDTO(),
    val isOwner: Boolean = false,
    val isParticipant: Boolean = false
)
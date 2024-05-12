package callofproject.dev.androidapp.presentation.project.project_participants

import callofproject.dev.androidapp.domain.dto.project.ProjectParticipantDTO

data class ProjectParticipantsState(
    val participants: List<ProjectParticipantDTO> = emptyList(),
    val isParticipantOrOwner: Boolean = false,
    val isOwner: Boolean = false
)

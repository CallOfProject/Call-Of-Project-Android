package callofproject.dev.androidapp.presentation.project.project_participants

sealed class ProjectParticipantsEvent {
    data class OnClickRemoveParticipant(val userId: String, val projectId: String) :
        ProjectParticipantsEvent()
}
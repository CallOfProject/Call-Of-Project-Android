package callofproject.dev.androidapp.presentation.project.project_overview

sealed class ProjectOverviewEvent {
    data class OnSendJoinRequestClick(val projectId: String) : ProjectOverviewEvent()
}
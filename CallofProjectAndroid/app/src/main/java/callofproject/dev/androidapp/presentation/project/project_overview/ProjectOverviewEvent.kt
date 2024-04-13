package callofproject.dev.androidapp.presentation.project.project_overview

sealed class ProjectOverviewEvent {
    data object OnClickApplyBtn : ProjectOverviewEvent()
}
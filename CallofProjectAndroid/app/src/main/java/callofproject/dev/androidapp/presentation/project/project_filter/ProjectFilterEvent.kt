package callofproject.dev.androidapp.presentation.project.project_filter

sealed class ProjectFilterEvent {
    data object OnClickFilterBtn : ProjectFilterEvent()
}
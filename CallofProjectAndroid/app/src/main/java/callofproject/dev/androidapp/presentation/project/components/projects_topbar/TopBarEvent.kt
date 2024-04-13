package callofproject.dev.androidapp.presentation.project.components.projects_topbar


sealed class TopBarEvent {
    data class OnClickProjectOverviewBtn(val projectId: String) : TopBarEvent()
    data class OnClickProjectDetailsBtn(val projectId: String) : TopBarEvent()
}
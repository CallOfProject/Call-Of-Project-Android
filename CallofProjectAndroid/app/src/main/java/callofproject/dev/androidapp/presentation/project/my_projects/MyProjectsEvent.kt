package callofproject.dev.androidapp.presentation.project.my_projects

sealed class MyProjectsEvent {
    data class OnClickProject(val projectId: String) : MyProjectsEvent()
}

package callofproject.dev.androidapp.presentation.project.my_projects

import callofproject.dev.androidapp.domain.dto.project.ProjectDetailDTO

data class MyProjectsState(
    val myProjects: List<ProjectDetailDTO> = emptyList(),
    val isLoading: Boolean = false
)
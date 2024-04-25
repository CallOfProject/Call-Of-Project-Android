package callofproject.dev.androidapp.presentation.project.project_filter

import callofproject.dev.androidapp.domain.dto.search.ProjectsDTO

data class ProjectFilterState(
    val isLoading: Boolean = false,
    val error: String = "",
    val projectFilterList: ProjectsDTO = ProjectsDTO()
)

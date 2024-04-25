package callofproject.dev.androidapp.presentation.project.project_filter

import callofproject.dev.androidapp.domain.dto.filter.ProjectFilterDTO

sealed class ProjectFilterEvent {
    data object OnClickSaveFilterBtn : ProjectFilterEvent()
    data class OnClickFilterProjectBtn(val projectFilterDTO: ProjectFilterDTO) :
        ProjectFilterEvent()

    data class OnClickProjectDiscoveryItem(
        val projectId: String
    ) : ProjectFilterEvent()
}
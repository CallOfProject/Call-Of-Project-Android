package callofproject.dev.androidapp.presentation.main_page

import callofproject.dev.androidapp.domain.dto.project.ProjectDiscoveryDTO

data class MainPageState(
    val isLoading: Boolean = false,
    val error: String = "",
    val projectDiscoveryList: List<ProjectDiscoveryDTO> = emptyList()
)
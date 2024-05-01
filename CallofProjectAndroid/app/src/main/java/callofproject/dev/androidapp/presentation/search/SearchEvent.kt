package callofproject.dev.androidapp.presentation.search

import callofproject.dev.androidapp.presentation.main_page.MainPageEvent
import java.util.UUID

sealed class SearchEvent {
    data class OnProjectClick(val projectId: String) : SearchEvent()
    data class OnUserClick(val userId: String) : SearchEvent()

    data class OnAddConnectionClick(val userId: UUID) : SearchEvent()

    data class OnSortProjects(
        val sortOption: String
    ) : SearchEvent()

    data class OnSortType(
        val sortType: String,
        val sortOption: String
    ) : SearchEvent()
}
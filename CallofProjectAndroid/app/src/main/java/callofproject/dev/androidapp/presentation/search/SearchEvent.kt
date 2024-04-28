package callofproject.dev.androidapp.presentation.search

import java.util.UUID

sealed class SearchEvent {
    data class OnProjectClick(val projectId: String) : SearchEvent()
    data class OnUserClick(val userId: String) : SearchEvent()

    data class OnAddConnectionClick(val userId: UUID) : SearchEvent()
}
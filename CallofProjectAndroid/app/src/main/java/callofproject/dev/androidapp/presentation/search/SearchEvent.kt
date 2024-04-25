package callofproject.dev.androidapp.presentation.search

sealed class SearchEvent {
    data class OnProjectClick(val projectId: String) : SearchEvent()
    data class OnUserClick(val userId: String) : SearchEvent()
}
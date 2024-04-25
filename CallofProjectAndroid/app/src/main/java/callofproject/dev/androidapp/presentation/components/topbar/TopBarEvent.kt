package callofproject.dev.androidapp.presentation.components.topbar

sealed class TopBarEvent {
    data class OnSearchEntered(val search: String) : TopBarEvent()
}
package callofproject.dev.androidapp.presentation.main_page

sealed class MainPageEvent {
    data class OnClickProjectDiscoveryItem(
        val projectId: String
    ) : MainPageEvent()
}
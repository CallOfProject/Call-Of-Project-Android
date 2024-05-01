package callofproject.dev.androidapp.presentation.main_page

sealed class MainPageEvent {
    data class OnClickProjectDiscoveryItem(
        val projectId: String
    ) : MainPageEvent()

    data class OnSortProjects(
        val sortOption: String
    ) : MainPageEvent()

    data class OnSortType(
        val sortType: String,
        val sortOption: String
    ) : MainPageEvent()
}
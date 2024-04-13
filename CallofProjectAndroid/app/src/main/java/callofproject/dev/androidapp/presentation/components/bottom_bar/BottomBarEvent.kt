package callofproject.dev.androidapp.presentation.components.bottom_bar


sealed class BottomBarEvent {
    data class Navigate(val route: String) : BottomBarEvent()
    data object NavigateToUserProfile : BottomBarEvent()

}
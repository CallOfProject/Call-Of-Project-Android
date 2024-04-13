package callofproject.dev.androidapp.util.route

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    data object NavigateUp : UiEvent()

    data class ShowSnackbar(val msg: UiText) : UiEvent()
    data class ShowToastMessage(val message: UiText) : UiEvent()
}
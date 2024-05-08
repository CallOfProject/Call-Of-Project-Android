package callofproject.dev.androidapp.util.route

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    data object NavigateUp : UiEvent()

    data class ShowSnackbar(val msg: UiText) : UiEvent()
    data class ShowToastMessage(val message: UiText) : UiEvent()
    data class ShowToastMessageViaStatus(val message: UiText, val success: Boolean) : UiEvent()

    data object Success : UiEvent()
    data object Failure : UiEvent()
}
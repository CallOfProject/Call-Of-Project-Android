package callofproject.dev.androidapp.presentation.components.topbar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.presentation.components.bottom_bar.BottomBarViewModel.Companion.selectedItemIndex
import callofproject.dev.androidapp.util.route.Route
import callofproject.dev.androidapp.util.route.UiEvent
import callofproject.dev.androidapp.websocket.WebSocketClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopBarViewModel @Inject constructor(
    private val webSocket: WebSocketClient,
    private val pref: IPreferences
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: TopBarEvent) = when (event) {
        is TopBarEvent.OnSearchEntered -> search(event.search)
        is TopBarEvent.OnLogoutClicked -> logoutApp()
    }

    private fun logoutApp() {
        viewModelScope.launch {
            selectedItemIndex.value = 0
            webSocket.disconnectWebSocket()
            pref.clearUserInformation()
            _uiEvent.send(UiEvent.Navigate(Route.LOGIN))
        }
    }

    private fun search(search: String) {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Navigate("${Route.SEARCH_RESULT}/$search"))
        }

    }
}
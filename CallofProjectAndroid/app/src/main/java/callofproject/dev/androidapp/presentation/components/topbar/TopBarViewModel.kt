package callofproject.dev.androidapp.presentation.components.topbar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.androidapp.util.route.Route
import callofproject.dev.androidapp.util.route.UiEvent
import callofproject.dev.androidapp.websocket.WebSocketClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopBarViewModel @Inject constructor(private val webSocket: WebSocketClient) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: TopBarEvent) = when (event) {
        is TopBarEvent.OnSearchEntered -> search(event.search)
    }

    private fun search(search: String) {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Navigate("${Route.SEARCH_RESULT}/$search"))
        }

    }

    fun stopWebsocket() {
        webSocket.disconnectWebSocket()
    }
}